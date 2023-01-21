package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.ReflectiveOperation
import com.rtkit.upsource_manager.bot.await
import com.rtkit.upsource_manager.bot.enums.EEmoji
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color

@ReflectiveOperation
class SlashCommand_Test : BotSlashCommandsHandler.ISlashCommandHandler() {
    private val logger: Logger = LogManager.getLogger(SlashCommand_Test::class.java)
    override val command: String = "send-test-message"
    override val description: String = "test"

    override suspend fun onCommand(event: SlashCommandInteractionEvent): String {
        sendTestMessage(event.channel)
        return "${EEmoji.STARS.emoji} Пользователь успешно инициализирован"
    }

    private suspend fun sendTestMessage(channel: MessageChannel) {
        try {
            val messageBuilder = MessageBuilder("MESSAGE1 \n")

            val embedBuilder1 = EmbedBuilder()
            embedBuilder1.addField(
                MessageEmbed.Field(
                    "Автор кода",
                    BotInstance.getUserMention("940863876390604800"),
                    true,
                    true
                )
            )
            embedBuilder1.setColor(Color.RED)
            messageBuilder.setEmbeds(embedBuilder1.build())

            val message = messageBuilder.build()
            channel.sendMessage(message).await()

        } catch (e: Exception) {
            logger.error("Ошибка при отправки сообщения", e)
        }
    }

}