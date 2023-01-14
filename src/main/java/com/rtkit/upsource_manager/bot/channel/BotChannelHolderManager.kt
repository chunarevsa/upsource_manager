package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.IRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.internal.entities.EntityBuilder
import org.springframework.scheduling.annotation.Scheduled
import com.rtkit.upsource_manager.bot.Config

/** Бот может контролировать несколько каналов (например, основной и тестовый).
 * Данный объект предоставляет возможность их независимого управления.
 * @author Johnson on 20.02.2021*/
object BotChannelHolderManager : ListenerAdapter() {
    private val holders = HashMap<String, BotChannelHolder>()

    fun addHolder(channel: TextChannel) = BotChannelHolder(channel).apply { holders[channel.id] = this }
    fun getHolder(channel: MessageChannel): BotChannelHolder? = holders[channel.id]

    suspend fun <T : IRequest> onPlatformAction(action: APipelineActionInfo<T>) {
        try {
            holders.forEach { it.value.onPlatformAction(action) }
        } catch (e: Exception) {
//            LOGGER.error("", e)
        }
    }

    @Scheduled(fixedRate = 1000)
    fun onTick() = holders.forEach {
        try {
            it.value.onTick()
        } catch (e: Exception) {
//            LOGGER.warn("", e)
        }
    }

    override fun onReady(event: ReadyEvent) {
        if (Config.messageIdStore.isNotEmpty()) {
            Config.messageIdStore.keys.forEach { channelId ->
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
                EntityBuilder.createActivity("за сборкой", null, Activity.ActivityType.WATCHING)
        }
    }


}