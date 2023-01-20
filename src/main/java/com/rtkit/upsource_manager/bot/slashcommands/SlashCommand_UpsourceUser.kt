package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.bot.ReflectiveOperation
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.enums.EEmoji

@ReflectiveOperation
class SlashCommand_UpsourceUser : BotSlashCommandsHandler.ISlashCommandHandler() {
    override val command: String = "upsource-user-mapping"
    override val description: String = "Связка или отвязка пользователя Upsource с пользователем Discord"

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "action", "Действие", true, false)
                .addChoice("Привязать пользователя", "add")
                .addChoice("Отвязать пользователя", "remove"),
            OptionData(OptionType.STRING, "upsource-user", "Пользователь Upsource (разделение через запятую)", true, false),
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
        val upsourceUsers = event.getOption("upsource-user")?.asString?.split(",")?.map { it.trim() }?.toSet()
            ?: return "${EEmoji.BLOCK.emoji} Пользователь upsource не найден!"

        if (event.getOption("action")?.asString == "add") {

            val container = Config.userMapping.computeIfAbsent(dcUser.id, { HashSet() })
            container.add(dcUser.name)
            container.add(dcUser.asMention)
            container.addAll(upsourceUsers)
            Config.save()

            return "${EEmoji.STARS.emoji} Пользователь `$upsourceUsers` связан с ${dcUser.asMention}"
        } else {

            if (dcUser.isBot) {
                Config.userMapping.forEach { it.value.removeAll(upsourceUsers) }
                Config.save()

                return "${EEmoji.STARS.emoji} Пользователь `$upsourceUsers` отвязан ото всех пользователей Дискорда!"
            } else {
                Config.userMapping[dcUser.id]?.removeAll(upsourceUsers)
                Config.save()

                return "${EEmoji.STARS.emoji} Пользователь `$upsourceUsers` отвязан от пользователя ${dcUser.asMention}"
            }
        }
    }
}