package com.rtkit.upsource_manager.bot.commands

import com.rtkit.upsource_manager.ReflectiveOperation
import com.rtkit.upsource_manager.bot.BotInstance
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageHistory
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.rtkit.upsource_manager.bot.Config

/** @author Johnson on 19.02.2021*/
@ReflectiveOperation
class CommandHandler_Clear : BotCommandHandler.ICommandHandler() {
    override val command: String = "clear"

    override fun onCommand(event: MessageReceivedEvent): Boolean {
        if (!BotInstance.isControlledChannel(event.channel)) return false
        clearTheChannel(event.channel as TextChannel)
        return true
    }

    companion object {
        /** Удаляет все не-служебные сообщения из канала */
        fun clearTheChannel(channel: TextChannel) {
            if (!BotInstance.isControlledChannel(channel)) return

            val messagesToDelete = ArrayList<Message>()
            val storeTags = Config.messageIdStore.computeIfAbsent(channel.id, { HashMap() })

            MessageHistory.getHistoryFromBeginning(channel).queue { hist ->
                hist.retrievedHistory.forEach { message ->
                    // Интро не трогаем
                    if (storeTags["INTRO"] == message.id) {
                        return@forEach
                    }
                    // Сообщения платформ не трогаем
                    for (platform in Config.messageIdStore[channel.id]?.keys ?: emptyList()) {
                        if (storeTags[platform] == message.id) {
                            return@forEach
                        }
                    }
                    // Все остальные сообщения удаляем
                    messagesToDelete.add(message)
                }

                BotInstance.deleteMessages(channel, messagesToDelete)
            }
        }
    }
}