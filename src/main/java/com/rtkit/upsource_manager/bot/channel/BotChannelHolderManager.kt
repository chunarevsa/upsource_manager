package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.bot.Config
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.internal.entities.EntityBuilder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/** Бот может контролировать несколько каналов (например, основной и тестовый).
 * Данный объект предоставляет возможность их независимого управления.
 */
@Service
object BotChannelHolderManager : ListenerAdapter() {
    private val logger: Logger = LogManager.getLogger(BotChannelHolderManager::class.java)

    private val holders = HashMap<String, BotChannelHolder>()

    fun addHolder(channel: TextChannel) = BotChannelHolder(channel).apply { holders[channel.id] = this }
    fun getHolder(channel: MessageChannel): BotChannelHolder? = holders[channel.id]

    @Scheduled(fixedRate = 10000)
    fun onTick() = holders.forEach {
        try {
            it.value.onTick()
        } catch (e: Exception) {
            logger.warn("", e)
        }
    }

    override fun onReady(event: ReadyEvent) {
        if (Config.channelStorage.isNotEmpty()) {
            Config.channelStorage.keys.forEach { channelId ->
                val channel = event.jda.getTextChannelById(channelId)
                if (channel != null) {
                    val holder = addHolder(channel)
                    runBlocking {
                        launch {
                            holder.initializeChannel()
                        }
                    }
                }
            }
            event.jda.presence.activity =
                EntityBuilder.createActivity("for reviews", null, Activity.ActivityType.WATCHING)
        }
    }


}