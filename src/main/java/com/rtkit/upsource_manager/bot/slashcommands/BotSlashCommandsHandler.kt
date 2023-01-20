package com.rtkit.upsource_manager.bot.slashcommands

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.enums.EEmoji
import com.rtkit.upsource_manager.bot.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner

/** @author karpov-em on 01.07.2022*/
object BotSlashCommandsHandler : ListenerAdapter() {
    private const val commandHandlerPackage = "com.rtkit.upsource_manager.bot.slashcommands"
    private val handlers =
        Reflections(commandHandlerPackage, SubTypesScanner()).getSubTypesOf(ISlashCommandHandler::class.java)
            .map { c -> c.getDeclaredConstructor().newInstance() }

    init {
//        LOGGER.info("Registered slash command handlers: ${handlers.joinToString(", ") { h -> h.command }}")
    }

    fun rebuildSlashCommands() {
        BotInstance.getGuilds().forEach { guild ->
            guild
                .updateCommands()
                .addCommands(handlers.map { Commands.slash(it.command, it.description).addOptions(it.getOptions()) })
                .queue {
//                    LOGGER.info("Slash commands pushed to Discord")
                }
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        event.deferReply().queue()
        BotInstance.log("${event.user.asMention} использует команду `${event.commandString}`")

        runBlocking {
            launch {
                if (event.user.isBot) {
                    event.reply("${EEmoji.BLOCK.emoji} Bots is not allowed to use commands!").queue()
                    return@launch
                }

                val handler = handlers.find { it.command == event.name } ?: let {
                    event.reply("${EEmoji.BLOCK.emoji} Команда не найдена").queue()
                    return@launch
                }

                val deniedMessage = handler.isCommandAllowed(event)
                if (deniedMessage != null) {
                    val message = event.hook.sendMessage(deniedMessage).await()
                    delay(10000)
                    message.delete().queue()
                    return@launch
                }

                val handledMessage = handler.onCommand(event)
                if (handledMessage != null) {
                    val message = event.hook.sendMessage(handledMessage).await()
                    delay(10000)
                    message.delete().queue()
                    return@launch
                } else {
                    event.reply("${EEmoji.BLOCK.emoji} Команда не обработана! См. логи!").queue()
                }
            }
        }
    }

    fun getHandler(command: String) = handlers.find { it.command == command }

    abstract class ISlashCommandHandler {
        abstract val command: String
        abstract val description: String
        open fun getOptions(): List<OptionData> = emptyList()

        open fun isCommandAllowed(event: SlashCommandInteractionEvent): String? {
            if (event.member?.hasPermission(Permission.ADMINISTRATOR) != true) {
                return "Недостаточно прав для использования этой команды!"
            }
            return null
        }

        abstract suspend fun onCommand(event: SlashCommandInteractionEvent): String?

        open fun upsertCommand() {
            BotInstance.getGuilds().forEach { guild ->
                guild.upsertCommand(Commands.slash(command, description).addOptions(getOptions())).queue()
            }
        }
    }
}