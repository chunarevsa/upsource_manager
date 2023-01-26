package com.rtkit.upsource_manager.bot

import com.rtkit.upsource_manager.bot.channel.BotChannelHolderManager
import com.rtkit.upsource_manager.bot.commands.BotCommandHandler
import com.rtkit.upsource_manager.bot.slashcommands.BotSlashCommandsHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.utils.Compression
import net.dv8tion.jda.internal.entities.EntityBuilder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * API: https://github.com/DV8FromTheWorld/JDA
 * Добавить бота: https://lumpics.ru/how-to-add-bot-to-discord-server/
 */
object BotInstance : ListenerAdapter() {
    private val logger: Logger = LogManager.getLogger(BotInstance::class.java)

    var jdaInstance: JDA? = null
        private set

    fun runBot() {
        val builder = JDABuilder.createDefault(Config.botToken)
        builder.setCompression(Compression.ZLIB)
        builder.setActivity(
            EntityBuilder.createActivity(
                "Сервис запускается...",
                null,
                Activity.ActivityType.CUSTOM_STATUS
            )
        )
        builder.addEventListeners(BotChannelHolderManager, this, BotSlashCommandsHandler, BotCommandHandler)

        logger.info("Connecting to Discord...")
        jdaInstance = builder.build().awaitReady()

        BotSlashCommandsHandler.rebuildSlashCommands()

        logger.info("Bot started")
    }

    fun stopBot() {
        if (jdaInstance != null) {
            try {
                log("SERVICE SHUTDOWN")
                jdaInstance!!.shutdown()
            } catch (t: Throwable) {
                logger.error("", t)
            }
        }
    }

    @Deprecated("async")
    fun deleteMessages(channel: TextChannel, messages: Collection<Message>, complete: (() -> Unit)? = null) {
        if (messages.isNotEmpty()) {
            try {
                logger.info("---------- Delete messages:")
                messages.forEach { message -> logger.info("${message.id} - ${message.contentRaw}") }
                if (messages.size == 1) {
                    channel.deleteMessageById(messages.first().id).queue { complete?.invoke() }
                } else {
                    channel.deleteMessages(messages.toList()).queue { complete?.invoke() }
                }
            } catch (e: IllegalArgumentException) {
                if (e.message?.contains("older") == true) {
                    sendTimedMessage(
                        channel,
                        "Не могу удалить некоторые сообщения при инициализации канала: __они слишком старые__. Если вы уверены, что они должны быть удалены - удалите их самостоятельно. В противном случае почините настройки бота."
                    )
                }
                throw e
            }
        } else {
            complete?.invoke()
        }
    }

    suspend fun deleteMessagesAsync(channel: TextChannel, messages: Collection<Message>) {
        if (messages.isNotEmpty()) {
            logger.info("---------- Delete messages:")
            messages.forEach { message -> logger.info("${message.id} - ${message.contentRaw}") }

            try {
                if (messages.size == 1) {
                    channel.deleteMessageById(messages.first().id).await()
                } else {
                    channel.deleteMessages(messages.toList()).await()
                }
            } catch (e: IllegalArgumentException) {
                if (e.message?.contains("older") == true) {
                    sendTimedMessage(
                        channel,
                        "Не могу удалить некоторые сообщения при инициализации канала: __они слишком старые__. Если вы уверены, что они должны быть удалены - удалите их самостоятельно. В противном случае почините настройки бота."
                    )
                }
                throw e
            }
        }
    }

    fun isControlledChannel(channel: MessageChannel) =
        Config.channelStorage.keys.contains(channel.id) || Config.channels.logging == channel.id || Config.channels.pushes == channel.id

    fun sendTimedMessage(channel: MessageChannel, text: String, timeoutSeconds: Long = 10) = runBlocking {
        launch {
            val message = channel.sendMessage(text).await()
            delay(timeoutSeconds * 1000)
            message.delete().await()
        }
    }

    fun sendTimedMessage(channel: MessageChannel, message: Message, timeoutSeconds: Long = 10) = runBlocking {
        launch {
            val message = channel.sendMessage(message).await()
            delay(timeoutSeconds * 1000)
            message.delete().await()
        }
    }

    fun getGuilds(): List<Guild> = jdaInstance?.guilds ?: emptyList()

    fun log(msg: String, dcOnly: Boolean = false) {
        if (!dcOnly) {
            logger.info(msg)
        }
        if (Config.channels.logging.isNotBlank()) {
            jdaInstance?.getTextChannelById(Config.channels.logging)
                ?.sendMessage(getMessageWithoutMentions("[`${sdf.format(Date())}`] $msg"))?.queue()
        }
    }

    private suspend fun searchUser(search: String): User? {
        val idx = Config.userMapping
            .map { mapping -> arrayOf(mapping.key, *mapping.value.map { it.toLowerCase() }.toTypedArray()) }
            .find { users -> users.contains(search.toLowerCase()) }
        return jdaInstance?.retrieveUserById(idx?.first() ?: search.toLowerCase())?.await()
    }

    private fun searchRole(search: String): Role? {
        jdaInstance?.guilds?.forEach { guild ->
            guild.roles.forEach { role ->
                if (role.name == search || role.id == search || role.asMention == search) {
                    return role
                }
            }
        }
        return null
    }

    suspend fun getUserMention(search: String) = searchUser(search)?.asMention ?: "`$search`"

    fun getRoleMention(search: String) = searchRole(search)?.asMention ?: "`$search`"

    private fun getMessageWithoutMentions(msg: String) =
        MessageBuilder().append(msg).denyMentions(Message.MentionType.USER, Message.MentionType.ROLE).build()

    fun getTextChannel(channelId: String) = jdaInstance?.getTextChannelById(channelId)
}

val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

suspend fun <T> RestAction<T>.await() = suspendCoroutine<T> { cont ->
    queue {
        cont.resume(it)
    }
}