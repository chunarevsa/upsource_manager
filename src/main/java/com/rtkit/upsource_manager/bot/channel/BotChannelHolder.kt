package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.ChannelStorage
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.await
import com.rtkit.upsource_manager.payload.upsource.review.Review
import com.rtkit.upsource_manager.services.EReviewExpiredStatus
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.MessageHistory
import net.dv8tion.jda.api.entities.TextChannel
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color

class BotChannelHolder(private val channel: TextChannel) {
    private val logger: Logger = LogManager.getLogger(BotChannelHolder::class.java)

    /** Ссылка на сообщение-интро этого канала */
    private var introMessage: Message? = null

    /** Храним маппу о каких ревью уже напоминали */
    private val reviewIds = mutableListOf<String>()

    suspend fun initializeChannel(): BotChannelHolder {
        deleteMessage()
        createIntroMessage()
        logger.info("Channel ${channel.name} initialization finishing...")
        Config.channelStorage.computeIfAbsent(channel.id) { ChannelStorage() }
        Config.save()
        return this
    }

    private suspend fun deleteMessage() {
        val messagesToDelete = ArrayList<Message>()
        val channelStorage = Config.channelStorage[channel.id]!!

        val hist = MessageHistory.getHistoryFromBeginning(channel).await()

        hist.retrievedHistory.forEach { message ->
            // Определяем сообщение-интро
            if (message.id == channelStorage.introId) {
                introMessage = message
                return@forEach
            }

            // Все остальные сообщения удаляем
            messagesToDelete.add(message)
        }

        BotInstance.deleteMessagesAsync(channel, messagesToDelete)
    }

    private suspend fun createIntroMessage() {
        when {
            introMessage == null -> {
                val it = channel.sendMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                Config.channelStorage[channel.id]!!.introId = it.id
                logger.info("Channel ${channel.name} creates intro message")
            }
            introMessage!!.contentRaw != Config.introMessage -> {
                val it = introMessage!!.editMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                Config.channelStorage[channel.id]!!.introId = it.id
                logger.info("Channel ${channel.name} edit intro message")
            }
            else -> {
                logger.info("Channel ${channel.name} intro message OK")
            }
        }
    }

    private fun getUserLogin(): String? {
        val users = Config.channelStorage[channel.id]!!.users
        return if (users.isNotEmpty()) users[0] else null
    }

    suspend fun updateReviewMessages(reviews: MutableMap<String, MutableList<Review>>) {
        deleteMessage()
        val userMap = Config.userMapping[getUserLogin()]!!

        val userReviews = reviews[userMap[2]]
        userReviews?.sortBy { it.createdAt }
        val map = userReviews?.map { review -> getMessageFromReview(review, userMap[1]) }

        map?.forEach { message -> channel.sendMessage(message).await() }
        userReviews?.forEach { review -> reviewIds.add(review.reviewId.reviewId) }

    }

    private suspend fun getMessageFromReview(review: Review, name: String): Message {
        val isNotified = reviewIds.contains(review.reviewId.reviewId)
        val messageBuilder = MessageBuilder()
        val embedBuilder = EmbedBuilder()
        embedBuilder.addField(
            MessageEmbed.Field(
                "Название ${review.reviewId.reviewId}",
                if (isNotified) name else getUserLogin()?.let { BotInstance.getUserMention(it) },
                true,
                true
            )
        )
        val color = when (review.getExpiredStatus()) {
            EReviewExpiredStatus.FRESH -> Color.GREEN
            EReviewExpiredStatus.ATTENTION -> Color.YELLOW
            EReviewExpiredStatus.FIRE -> Color.RED
            EReviewExpiredStatus.EXPIRED -> Color.BLACK
            else -> Color.BLACK
        }
        embedBuilder.setColor(color)
        messageBuilder.setEmbeds(embedBuilder.build())

        return messageBuilder.build()
    }

}