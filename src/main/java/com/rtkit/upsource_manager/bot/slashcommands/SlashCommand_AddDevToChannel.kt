package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.ReflectiveOperation
import com.rtkit.upsource_manager.bot.enums.EEmoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@ReflectiveOperation
class SlashCommand_AddDevToChannel : BotSlashCommandsHandler.ISlashCommandHandler() {
    private val logger: Logger = LogManager.getLogger(SlashCommand_AddDevToChannel::class.java)
    override val command: String = "init-user"
    override val description: String = "Добавление discord пользователя"

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "action", "Действие", true, false)
                .addChoice("Привязать пользователя", "add")
                .addChoice("Отвязать пользователя", "remove")
                .addChoice("Добавить админа", "admin"),

            OptionData(
                OptionType.USER,
                "discord-user",
                "Пользователь Discord (для отвязки ото всех Discord пользователей выберите бота)",
                true,
                false
            ),
        )
    }

    override suspend fun onCommand(event: SlashCommandInteractionEvent): String {
        val dcUser = event.getOption("discord-user")?.asUser
            ?: return "${EEmoji.BLOCK.emoji} Пользователь discord не найден!"
        // Проверяем есть ли связка discordUser-UpsourceUser
        Config.userMap.getOrDefault(event.user.id, null)
            ?: return "${EEmoji.BLOCK.emoji} Discord пользователь не привязан к Upsource пользователю! \n" +
                    "Используй команду /add-upsource-user"

        when (event.getOption("action")?.asString) {
            "add" -> {
                Config.channelStorage[event.channel.id]?.user = dcUser.id
                Config.save()
                logger.info("Пользователь успешно инициализирован: ${dcUser.id}")
                // TODO сделать запуск апдейта. см. InitNewDiscordChannel

                return "${EEmoji.STARS.emoji} Пользователь успешно инициализирован \n" +
                        "${EEmoji.STARS.emoji} Загружаю список ревью..."
            }
            "remove" -> {
                return if (dcUser.isBot) {
                    Config.channelStorage[event.channel.id]?.user = ""
                    Config.channelStorage[event.channel.id]?.admins?.clear()
                    Config.save()

                    "${EEmoji.STARS.emoji} Пользователь отключен от канала!"
                } else {
                    logger.error("Произошла ошибка при отключении от канала. Канал: ${event.channel.id} Пользователь: ${event.user.id}")
                    "${EEmoji.BLOCK.emoji} Произошла ошибка при отключении от канала"
                }
            }
            "admin" -> {
                Config.channelStorage[event.channel.id]?.admins?.add(dcUser.id)
                Config.save()

                return "${EEmoji.STARS.emoji} Пользователь успешно инициализирован"
            }

        }
        return EEmoji.BLOCK.emoji
    }
}