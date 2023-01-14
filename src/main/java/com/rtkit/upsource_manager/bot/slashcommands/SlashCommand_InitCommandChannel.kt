package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.ReflectiveOperation
import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.channel.BotChannelHolderManager
import com.rtkit.upsource_manager.bot.enums.EReactionType
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import com.rtkit.upsource_manager.bot.Config

/** @author karpov-em on 01.07.2022*/
@ReflectiveOperation
class SlashCommand_InitCommandChannel : BotSlashCommandsHandler.ISlashCommandHandler() {
    override val command: String = "gitlab-channel-init"
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
            return "${EReactionType.FAIL.emoji} Недостаточно прав для использования этой команды!"
        }
        return null
    }

    override suspend fun onCommand(event: SlashCommandInteractionEvent): String {
        when (event.getOption("type")?.asString) {
            "main" -> {
                if (Config.messageIdStore.containsKey(event.textChannel.id)) {
                    return "${EReactionType.FAIL.emoji} Нельзя изменить уже созданный основной канал!"
                }

                try {
                    BotChannelHolderManager.addHolder(event.textChannel).initializeChannel()
                    BotSlashCommandsHandler.getHandler("bot-test-action")?.upsertCommand()
                } catch (e: Exception) {
                    Config.load()
                    BotInstance.sendTimedMessage(
                        event.channel,
                        "${EReactionType.FAIL.emoji} Не удалось инициализировать канал. Смотри логи."
                    )
//                    LOGGER.error("", e)
                }

                BotInstance.log("${event.user.asMention} зарегистрировал основной канал")
                // init messages
                return "${EReactionType.SUCCESS.emoji} Основной канал успешно зарегистрирован! Добавьте платформы!"
            }
            "logging" -> {
                Config.channels.logging = event.messageChannel.id
                Config.save()
                BotInstance.log("${event.user.asMention} зарегистрировал канал логирования")
                return "${EReactionType.SUCCESS.emoji} Канал логирования успешно зарегистрирован!"
            }
            "pushes" -> {
                Config.channels.pushes = event.messageChannel.id
                Config.save()
                BotInstance.log("${event.user.asMention} зарегистрировал канал логирования пушей")
                return "${EReactionType.SUCCESS.emoji} Канал логирования пушей успешно зарегистрирован!"
            }
        }

        return "*Стрекотание сверчков*"
    }
}