package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.await
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageHistory
import net.dv8tion.jda.api.entities.TextChannel
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class BotChannelHolder(private val channel: TextChannel) {
    private val logger: Logger = LogManager.getLogger(BotChannelHolder::class.java)

    /** Ссылка на сообщение-интро этого канала */
    private var introMessage: Message? = null

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

            // Все остальные сообщения удаляем
            messagesToDelete.add(message)
        }

        BotInstance.deleteMessagesAsync(channel, messagesToDelete)
        createIntroMessage()

        logger.info("Channel ${channel.name} initialization finishing...")
        Config.save()

        return this
    }

    private suspend fun createIntroMessage() {
        when {
            introMessage == null -> {
                val it = channel.sendMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                Config.messageIdStore.computeIfAbsent(it.channel.id, { HashMap() })["INTRO"] = it.id
                logger.info("Channel ${channel.name} creates intro message")
            }
            introMessage!!.contentRaw != Config.introMessage -> {
                val it = introMessage!!.editMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                Config.messageIdStore.computeIfAbsent(it.channel.id, { HashMap() })["INTRO"] = it.id
                logger.info("Channel ${channel.name} edit intro message")
            }
            else -> {
                logger.info("Channel ${channel.name} intro message OK")
            }
        }
    }

    fun onTick() {
        logger.error("добавить тик BotChannelHolder")
    }
}