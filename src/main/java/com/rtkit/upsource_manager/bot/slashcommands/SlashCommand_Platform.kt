package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.ReflectiveOperation
import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.channel.BotChannelHolderManager
import com.rtkit.upsource_manager.bot.enums.EReactionType
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import com.rtkit.upsource_manager.bot.Config

/** @author karpov-em on 01.07.2022*/
@ReflectiveOperation
class SlashCommand_Platform : BotSlashCommandsHandler.ISlashCommandHandler() {
    override val command: String = "gitlab-platform"
    override val description: String = "Добавление или удаление площадки сборки"

    override fun isCommandAllowed(event: SlashCommandInteractionEvent): String? {
        if (event.member?.hasPermission(Permission.ADMINISTRATOR) != true) {
            return "${EReactionType.FAIL.emoji} Недостаточно прав для использования этой команды!"
        }
        if (Config.messageIdStore.keys.size < 1) {
            return "${EReactionType.FAIL.emoji} Не настроен канал оповещений. Используйте команду `/bot-channel-init` в требуемом канале."
        }
        if (!Config.messageIdStore.containsKey(event.textChannel.id)) {
            return "${EReactionType.FAIL.emoji} Данный канал не является каналом уведомлений, используйте команду в одном из каналов: ${
                Config.messageIdStore.keys.map {
                    event.jda.getTextChannelById(
                        it
                    )?.asMention
                }.joinToString(", ")
            }"
        }
        val platform = event.getOption("platform")?.asString
        if (platform == "INTRO") {
            return "${EReactionType.FAIL.emoji} Такое название платформы зарезервировано системой и не может быть использовано!"
        }
        return null
    }

    override fun getOptions(): List<OptionData> {
        return listOf(
            OptionData(OptionType.STRING, "action", "Действие", true, false)
                .addChoice("Добавить платформу", "add")
                .addChoice("Удалить платформу", "remove"),
            OptionData(OptionType.STRING, "platform", "Пользователь GIT", true, false),
        )
    }

    override suspend fun onCommand(event: SlashCommandInteractionEvent): String {
        val platform =
            event.getOption("platform")?.asString ?: return "${EReactionType.FAIL.emoji} Пользователь git не найден!"

        val holder = BotChannelHolderManager.getHolder(event.channel)

        if (event.getOption("action")?.asString == "add") {
            if (Config.messageIdStore[event.channel.id]?.keys?.contains(platform) == true) {
                return "${EReactionType.FAIL.emoji} На данном канале уже зарегистрирована платформа с таким названием!"
            }
            if (holder != null) {
                runBlocking {
                    launch {
                        holder.createPlatformMessage(platform)
                        Config.save()
                        BotSlashCommandsHandler.getHandler("bot-test-action")?.upsertCommand()
                    }
                }
                BotInstance.log("Пользователь ${event.user.asMention} добавил платформу `$platform`")
                return "${EReactionType.SUCCESS.emoji} Платформа `$platform` добавлена"
            } else {
                return "${EReactionType.FAIL.emoji} Платформа не инициализирована - не создан менеджер данного канала!"
            }
        } else {
            if (holder != null) {
                runBlocking {
                    launch {
                        holder.removePlatform(platform)
                    }
                }
                BotInstance.log("Пользователь ${event.user.asMention} удалил платформу `$platform`")
                BotSlashCommandsHandler.getHandler("bot-test-action")?.upsertCommand()
                return "${EReactionType.SUCCESS.emoji} Платформа `$platform` удалена"
            } else {
                return "${EReactionType.FAIL.emoji} Платформа `$platform` не найдена в этом канале"
            }
        }
    }
}