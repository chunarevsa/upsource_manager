package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.ChannelStorage
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
    private var channelStorage = Config.channelStorage.computeIfAbsent(channel.id, { ChannelStorage() })

    suspend fun initializeChannel(): BotChannelHolder {
        val messagesToDelete = ArrayList<Message>()
        val hist = MessageHistory.getHistoryFromBeginning(channel).await()

        hist.retrievedHistory.forEach { message ->
            // Определяем сообщение-интро
            if (message.id == channelStorage.introId) {
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
                channelStorage.introId = it.id
                logger.info("Channel ${channel.name} creates intro message")
            }
            introMessage!!.contentRaw != Config.introMessage -> {
                val it = introMessage!!.editMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                channelStorage.introId = it.id
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