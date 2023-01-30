package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.bot.ReflectiveOperation
import com.rtkit.upsource_manager.bot.await
import com.rtkit.upsource_manager.bot.enums.EEmoji
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color

@ReflectiveOperation
class SlashCommand_Test : BotSlashCommandsHandler.ISlashCommandHandler() {
    private val logger: Logger = LogManager.getLogger(SlashCommand_Test::class.java)
    override val command: String = "send-test-message"
    override val description: String = "test"

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "text-message", "Текст сообщения", true, false),
            OptionData(OptionType.STRING, "embed-name", "Имя блока", false, false),
            OptionData(OptionType.STRING, "embed-value", "Значение блока", false, false),
        )
    }

    override suspend fun onCommand(event: SlashCommandInteractionEvent): String {
        val text = event.getOption("text-message")?.asString
            ?: "${EEmoji.BLOCK.emoji} Текст не может быть пустым"
        val embedName = event.getOption("embed-name")?.asString
        val embedValue = event.getOption("embed-value")?.asString
            ?: "Стандартное значение блока"

        sendTestMessage(event.channel, text, embedName, embedValue)

        return "${EEmoji.STARS.emoji} Команда выполнена"
    }


    private suspend fun sendTestMessage(channel: MessageChannel, text: String, embedName: String?, embedValue: String) {
        try {
            val messageBuilder = MessageBuilder(text)
            if (!embedName.isNullOrEmpty()) {
                val embedBuilder = EmbedBuilder()
                embedBuilder.addField(
                    MessageEmbed.Field(
                        embedName,
                        embedValue,
                        true,
                        true
                    )
                )
                embedBuilder.setFooter("940863876390604800")
                embedBuilder.setColor(Color.RED)
                messageBuilder.setEmbeds(embedBuilder.build())

            }

            val message = messageBuilder.build()
            channel.sendMessage(message).await()

        } catch (e: Exception) {
            logger.error("Ошибка при отправки сообщения", e)
        }
    }

}