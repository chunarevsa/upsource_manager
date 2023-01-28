package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.ReflectiveOperation
import com.rtkit.upsource_manager.bot.UserMap
import com.rtkit.upsource_manager.bot.enums.EEmoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

@ReflectiveOperation
class SlashCommand_AddUpsourceUser : BotSlashCommandsHandler.ISlashCommandHandler() {
    override val command: String = "upsource-user-mapping"
    override val description: String = "Связка или отвязка пользователя Upsource с пользователем Discord"

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "action", "Действие", true, false)
                .addChoice("Привязать пользователя", "add")
                .addChoice("Отвязать пользователя", "remove"),
            OptionData(OptionType.STRING, "upsource-user", "Пользователь Upsource", true, false),
            OptionData(
                OptionType.USER,
                "discord-user",
                "Пользователь Discord",
                true,
                false
            ),
        )
    }


    override suspend fun onCommand(event: SlashCommandInteractionEvent): String {
        val dcUser = event.getOption("discord-user")?.asUser
            ?: return "${EEmoji.BLOCK.emoji} Пользователь discord не найден!"
        val upsourceLogin = event.getOption("upsource-user")?.asString
            ?: return "${EEmoji.BLOCK.emoji} Пользователь upsource не найден!"


        if (!Config.upsourceUserLogin.contains(upsourceLogin))
            return "${EEmoji.BLOCK.emoji} Пользователь $upsourceLogin upsource не найден! Повторите попытку"

        if (event.getOption("action")?.asString == "add") {
            val userMap = Config.userMap.computeIfAbsent(dcUser.id, { UserMap() })
            userMap.discordUsername = dcUser.name
            userMap.discordUserMention = dcUser.asMention
            userMap.upsourceLogin = upsourceLogin
            Config.save()

            return "${EEmoji.STARS.emoji} Пользователь `$upsourceLogin` связан с ${dcUser.asMention}"
        } else {
            Config.userMap.remove(dcUser.id)
            Config.channelStorage.forEach { channel -> if (channel.value.user == dcUser.id) channel.value.user = "" }
            Config.save()

            return "${EEmoji.STARS.emoji} Пользователь `$upsourceLogin` отвязан от пользователя ${dcUser.asMention}"
        }
    }
}
