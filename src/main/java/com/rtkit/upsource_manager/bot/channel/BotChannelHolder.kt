package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.IRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageHistory
import net.dv8tion.jda.api.entities.TextChannel
import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.await
import com.rtkit.upsource_manager.bot.channel.action.GenericAction
import com.rtkit.upsource_manager.bot.enums.EPlatformBuildActionType
import com.rtkit.upsource_manager.bot.enums.EReactionType

class BotChannelHolder(private val channel: TextChannel) {
    /** Ссылка на сообщение-интро этого канала */
    private var introMessage: Message? = null

    /** Таблица название платформы на сообщение в канале <platform, MessageInstance> */
    private val platformMessages = HashMap<String, Message>()

    suspend fun initializeChannel(): BotChannelHolder {
        val messagesToDelete = ArrayList<Message>()
        val storeTags = Config.messageIdStore.computeIfAbsent(channel.id, { HashMap() })

        val hist = MessageHistory.getHistoryFromBeginning(channel).await()

        hist.retrievedHistory.forEach { message ->
            // Определяем сообщение-интро
            if (storeTags["INTRO"] == message.id) {
                introMessage = message
                return@forEach
            }

            // Определяем все сообщения платформ
            for ((platform, messageId) in storeTags.filter { it.key != "INTRO" }) {
                if (message.id == messageId) {
                    platformMessages[platform] = message
                    return@forEach
                }
            }

            // Все остальные сообщения удаляем
            messagesToDelete.add(message)
        }

        // Если сообщение-интро не найдено - сносим все платформенные. Интро должно быть первым.
        if (introMessage == null) {
            messagesToDelete.addAll(platformMessages.values)
            platformMessages.clear()
        }

        BotInstance.deleteMessagesAsync(channel, messagesToDelete)
        createIntroMessage()
        createNotFoundPlatformMessages()

//        LOGGER.info("Channel ${channel.name} initialization finishing...")
        Config.save()

        return this
    }

    private suspend fun createIntroMessage() {
        when {
            introMessage == null -> {
                val it = channel.sendMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                Config.messageIdStore.computeIfAbsent(it.channel.id, { HashMap() })["INTRO"] = it.id
//                LOGGER.info("Channel ${channel.name} creates intro message")
            }
            introMessage!!.contentRaw != Config.introMessage -> {
                val it = introMessage!!.editMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                Config.messageIdStore.computeIfAbsent(it.channel.id, { HashMap() })["INTRO"] = it.id
//                LOGGER.info("Channel ${channel.name} edit intro message")
            }
            else -> {
//                LOGGER.info("Channel ${channel.name} intro message OK")
            }
        }
    }

    suspend fun createPlatformMessage(platform: String): Message {
        val message = channel.sendMessage(platformFormat(platform)).await()
        platformMessages[platform] = message
        Config.messageIdStore.computeIfAbsent(message.channel.id, { HashMap() })[platform] = message.id

        message.addReaction(EReactionType.START.toEmoji()).queue()
        message.addReaction(EReactionType.SUCCESS.toEmoji()).queue()
        message.addReaction(EReactionType.FAIL.toEmoji()).queue()

//        LOGGER.info("Channel ${channel.name} creates platform message")

        return message
    }

    private suspend fun createNotFoundPlatformMessages() {
        Config.messageIdStore[channel.id]?.keys?.filter { it != "INTRO" }?.forEach { platform ->
            if (!platformMessages.containsKey(platform)) {
                // Если не нашли сообщений платформ - создаем их
                createPlatformMessage(platform)
            } else {
                val message = platformMessages[platform]!!
                val messageShouldBe = platformFormat(platform)
                if (message.contentRaw != messageShouldBe) {
//                    LOGGER.info("Channel ${channel.name} edit platform message")
                    message.editMessage(messageShouldBe)
                }
            }
        }

//        LOGGER.info("Channel ${channel.name} platform messages OK")
    }

    private fun platformFormat(platform: String) = ">>> \n${Config.Emoji_PC} __${platform}__"

    /** <platform, CurrentAction> */
    private val platformActionInfos: MutableMap<String, APipelineActionInfo<IRequest>> = HashMap()

    suspend fun <T : IRequest> onPlatformAction(action: APipelineActionInfo<T>) {
        if (action.type == EPlatformBuildActionType.None) {
            return
        }

        // Если в этом холдере нет информации о запрошенной платформе - нас оно не интересует
        if (!platformMessages.containsKey(action.platform)) {
            return
        }

        action.channel = this.channel

        when (action.type) {
            EPlatformBuildActionType.BuildStart -> {
            }
            EPlatformBuildActionType.BuildFailed,
            EPlatformBuildActionType.SonarFailed,
            EPlatformBuildActionType.DeployFialed,
            EPlatformBuildActionType.DeployCanceled -> {
                action.listenerMentionables.add(action.userId)
            }
            EPlatformBuildActionType.TimedOut -> {
                // todo! а как сюда вписать целую роль?
            }
            EPlatformBuildActionType.DeploySuccess -> {
            }
            EPlatformBuildActionType.None -> TODO()
            EPlatformBuildActionType.BuildSuccess -> TODO()
            EPlatformBuildActionType.DeployStarted -> TODO()
        }
        action.listenerMentionables.addAll(
            platformMessages[action.platform]
                ?.retrieveReactionUsers(action.type.reaction.toEmoji())
                ?.await()
                ?.filter { !it.isBot && !it.isSystem }
                ?.map { it.id }
                ?: emptyList()
        )

        // Текущая активность
        val currentActionInfo = platformActionInfos[action.platform]
        if (currentActionInfo != null) {
            action.applyPreviousActionInfo(currentActionInfo)
        }

        // Список подписавшихся изменился?
        val mentionsChanged = currentActionInfo?.listenerMentionables != action.listenerMentionables
        // Новое состояние должно быть в новом сообщении? (нет старого ИЛИ новое более раннее ИЛИ старое финальное, а новое не такое же)
        val shouldCreate = currentActionInfo == null ||
                action.type.statusOrder < currentActionInfo.type.statusOrder ||
                (currentActionInfo.type.isFinal && action.type != currentActionInfo.type)
        if (shouldCreate || mentionsChanged) {
            createActionAsync(action)
        } else {
            updateMessage(action)
        }

        platformActionInfos[action.platform] = action as APipelineActionInfo<IRequest>
    }

    private suspend fun <T : IRequest> createActionAsync(action: APipelineActionInfo<T>) {
        val currentActionInfo = platformActionInfos[action.platform]
        if (currentActionInfo != null) {
            currentActionInfo.message?.delete()?.queue()
            platformActionInfos.remove(currentActionInfo.platform)
        }
        action.message = channel.sendMessage(action.uncommitedMessage).await()
    }

    private suspend fun <T : IRequest> updateMessage(action: APipelineActionInfo<T>) {
        val currentActionInfo = platformActionInfos[action.platform]
        val message = currentActionInfo?.message ?: action.message
        action.message = if (message == null) {
            channel.sendMessage(action.uncommitedMessage).await()
        } else {
            message.editMessage(action.uncommitedMessage).await()
        }
    }

    /** Вызывается раз в секунду для всех контролируемых каналов */
    fun onTick() {
        platformActionInfos.forEach { (platform, action) ->
            if (action.timeoutStamp > 0 && System.currentTimeMillis() > action.timeoutStamp) {
                when (action.type) {
                    // За 15 минут после старта билда ничего не произошло
                    EPlatformBuildActionType.BuildStart -> {
                        // todo! в статус таймаут - если после этого что-то произойдёт - то оно само раздуплится
                    }
                    // За минуту после успеха билда ничего не произошло (деплой не начался)
                    EPlatformBuildActionType.BuildSuccess -> {
                        // todo! в статус таймаут - если после этого что-то произойдёт - то оно само раздуплится
                    }
                    // За 30 минут после отказа билда ничего не произошло (не запустили билд заново)
                    EPlatformBuildActionType.BuildFailed -> {
                        // todo! в статус таймаут - если после этого что-то произойдёт - то оно само раздуплится
                    }
                    EPlatformBuildActionType.SonarFailed -> {
                        // todo
                    }
                    // За 15 минут после старта деплоя ничего не произошло (долго деплоится?)
                    EPlatformBuildActionType.DeployStarted -> {
                        // todo! в статус таймаут - если после этого что-то произойдёт - то оно само раздуплится
                    }
                    // Через минуту после успеха деплоя удаляем сообщение, если еще не было удалено
                    EPlatformBuildActionType.DeploySuccess -> {
                        action.message?.delete()?.queue()
                        action.message = null
                    }
                    // За 30 минут после отказа деплоя ничего не произошло (не запустили билд заново)
                    EPlatformBuildActionType.DeployFialed -> {
                        runBlocking {
                            launch {
                                onPlatformAction(GenericAction(action.platform).applyTimeoutState(true))
                            }
                        }
                    }
                    // За 30 минут после отмены деплоя ничего не произошло (не запустили билд заново)
                    EPlatformBuildActionType.DeployCanceled -> {
                        runBlocking {
                            launch {
                                onPlatformAction(GenericAction(action.platform).applyTimeoutState(true))
                            }
                        }
                    }

                    EPlatformBuildActionType.None -> TODO()
                    EPlatformBuildActionType.TimedOut -> TODO()
                }
            }
        }
    }

    suspend fun removePlatform(platform: String) {
        val message = platformMessages[platform]
        if (message != null) {
            message.delete().await()
            platformMessages.remove(platform)
            Config.messageIdStore[message.channel.id]?.remove(platform)
            Config.save()
        }
    }
}