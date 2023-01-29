package com.rtkit.upsource_manager.bot.commands

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import com.rtkit.upsource_manager.bot.BotInstance
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/** @author Johnson on 19.02.2021*/
object BotCommandHandler : ListenerAdapter() {
    private val logger: Logger = LogManager.getLogger(BotCommandHandler::class.java)

    private const val commandHandlerPackage = "com.rtkit.upsource_manager.bot.commands"
    private val handlers =
        Reflections(commandHandlerPackage, SubTypesScanner()).getSubTypesOf(ICommandHandler::class.java)
            .map { c -> c.getDeclaredConstructor().newInstance() }

    init {
        logger.info("==== Registered command handlers: ${handlers.joinToString(", ") { h -> h.command }}")
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        // Сообщения ботов не трогаем
        if (event.author.isBot) return

        var commandHandled = false
        // event.message.messageReference?.messageId - это при упоминании другого сообщения

        if (event.message.contentDisplay.startsWith("!")) {
            logger.info("${event.author.asTag}: ${event.message.contentRaw}")
            handlers.firstOrNull {
                // Некоторые хендлеры только для админов
                if (it.adminOnly && event.member?.hasPermission(Permission.ADMINISTRATOR) != true) return@firstOrNull false

                val content = event.message.contentDisplay.substring(1)
                // Перебираем все хендлеры, имеющие нужную команду, до тех пор, пока один их них не завершится успешно
                if (content == it.command || content.startsWith("${it.command} ")) {
                    commandHandled = commandHandled || it.onCommand(event)
                    return@firstOrNull commandHandled
                }
                // Не нашли
                return@firstOrNull false
            }
        }
        if (commandHandled) {
            logger.warn("User ${event.author.name} perform command: ${event.message.contentDisplay}")
        }
        // Если команда обработана, либо сообщение от не-бота в контролируемом канале - удаляем сообщение
        if (commandHandled || BotInstance.isControlledChannel(event.channel)) {
            logger.info("==== Message deleted: ${event.message.contentRaw}")
            event.message.delete().queue()
        }
    }

    abstract class ICommandHandler {
        open val adminOnly: Boolean = false
        abstract val command: String
        abstract fun onCommand(event: MessageReceivedEvent): Boolean
    }
}