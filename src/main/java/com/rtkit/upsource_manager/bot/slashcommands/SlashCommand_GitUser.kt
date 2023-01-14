package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.ReflectiveOperation
import com.rtkit.upsource_manager.bot.enums.EReactionType
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import com.rtkit.upsource_manager.bot.Config

/** @author karpov-em on 01.07.2022*/
@ReflectiveOperation
class SlashCommand_GitUser : BotSlashCommandsHandler.ISlashCommandHandler() {
    override val command: String = "gitlab-user-mapping"
    override val description: String = "Связка или отвязка пользователя Git с пользователем Discord"

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "action", "Действие", true, false)
                .addChoice("Привязать пользователя", "add")
                .addChoice("Отвязать пользователя", "remove"),
            OptionData(OptionType.STRING, "git-user", "Пользователь GIT (разделение через запятую)", true, false),
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
            ?: return "${EReactionType.FAIL.emoji} Пользователь discord не найден!"
        val gitUsers = event.getOption("git-user")?.asString?.split(",")?.map { it.trim() }?.toSet()
            ?: return "${EReactionType.FAIL.emoji} Пользователь git не найден!"

        if (event.getOption("action")?.asString == "add") {

            val container = Config.userMapping.computeIfAbsent(dcUser.id, { HashSet() })
            container.add(dcUser.name)
            container.add(dcUser.asMention)
            container.addAll(gitUsers)
            Config.save()

            return "${EReactionType.SUCCESS.emoji} Пользователь `$gitUsers` связан с ${dcUser.asMention}"
        } else {

            if (dcUser.isBot) {
                Config.userMapping.forEach { it.value.removeAll(gitUsers) }
                Config.save()

                return "${EReactionType.SUCCESS.emoji} Пользователь `$gitUsers` отвязан ото всех пользователей Дискорда!"
            } else {
                Config.userMapping[dcUser.id]?.removeAll(gitUsers)
                Config.save()

                return "${EReactionType.SUCCESS.emoji} Пользователь `$gitUsers` отвязан от пользователя ${dcUser.asMention}"
            }
        }
    }
}