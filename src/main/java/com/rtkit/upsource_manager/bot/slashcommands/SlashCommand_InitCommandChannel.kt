package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.bot.ReflectiveOperation
import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.enums.EEmoji
import com.rtkit.upsource_manager.bot.channel.BotChannelHolderManager
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@ReflectiveOperation
class SlashCommand_InitCommandChannel : BotSlashCommandsHandler.ISlashCommandHandler() {
    private val logger: Logger = LogManager.getLogger(SlashCommand_InitCommandChannel::class.java)

    override val command: String = "upsource-channel-init"
    override val description: String = "Инициализация каналов, управляемых ботом"

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "type", "Тип канала", true, false)
                .addChoice("Основной канал уведомлений", "main")
                .addChoice("Логирование бота", "logging")
                .addChoice("Логирование пушей", "pushes"),
        )
    }

    override fun isCommandAllowed(event: SlashCommandInteractionEvent): String? {
        if (event.member?.hasPermission(Permission.ADMINISTRATOR) != true) {
            return "${EEmoji.BLOCK.emoji} Недостаточно прав для использования этой команды!"
        }
        return null
    }

    override suspend fun onCommand(event: SlashCommandInteractionEvent): String {
        when (event.getOption("type")?.asString) {
            "main" -> {
                if (Config.channelStorage.containsKey(event.textChannel.id)) {
                    return "${EEmoji.BLOCK.emoji} Нельзя изменить уже созданный основной канал!"
                }

                try {
                    BotChannelHolderManager.addHolder(event.textChannel).initializeChannel()
                    BotSlashCommandsHandler.getHandler("bot-test-action")?.upsertCommand()
                } catch (e: Exception) {
                    Config.load()
                    BotInstance.sendTimedMessage(
                        event.channel,
                        "${EEmoji.BLOCK.emoji} Не удалось инициализировать канал. Смотри логи."
                    )
                    logger.error("", e)
                }

                BotInstance.log("${event.user.asMention} зарегистрировал основной канал")
                // init messages
                return "${EEmoji.STARS.emoji} Основной канал успешно зарегистрирован!"
            }
            "logging" -> {
                Config.channels.logging = event.messageChannel.id
                Config.save()
                BotInstance.log("${event.user.asMention} зарегистрировал канал логирования")
                return "${EEmoji.STARS.emoji} Канал логирования успешно зарегистрирован!"
            }
            "pushes" -> {
                Config.channels.pushes = event.messageChannel.id
                Config.save()
                BotInstance.log("${event.user.asMention} зарегистрировал канал логирования пушей")
                return "${EEmoji.STARS.emoji} Канал логирования пушей успешно зарегистрирован!"
            }
        }

        return "*Стрекотание сверчков*"
    }
}