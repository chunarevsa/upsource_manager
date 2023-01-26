package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.payload.upsource.review.Review
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

    suspend fun updateReviewMessages(reviews: MutableMap<String, MutableList<Review>>) {
        holders.forEach { holder -> holder.value.updateReviewMessages(reviews) }
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