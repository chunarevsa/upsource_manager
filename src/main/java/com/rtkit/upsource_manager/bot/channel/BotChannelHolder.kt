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
import java.util.stream.Collectors

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

    suspend fun updateReviewMessages(reviewsFromReq: MutableMap<String, MutableList<Review>>) {
        val userMap = Config.userMapping[getUserLogin()]!!
        val channelStorage = Config.channelStorage[channel.id]!!
        val name = if (userMap.size >= 2) userMap[2] else throw Exception()
        val userReviews: MutableList<Review>

        // Не трогаем интро
        val messageList = MessageHistory.getHistoryFromBeginning(channel).await().retrievedHistory.toMutableList()
        messageList.removeIf { it.id == channelStorage.introId }

        if (!reviewsFromReq.containsKey(name)) {
            // Нет ревью на этом пользователе, заполняем поздравлением, остальные удаляем
            messageList[0].editMessage(createCongratulationMessage())
            messageList.removeAt(0)
            if (messageList.isNotEmpty()) BotInstance.deleteMessagesAsync(channel, messageList)
        } else if (messageList.isEmpty() && reviewsFromReq.containsKey(name)) {
            // Нет сообщений, но есть ревью
            userReviews = reviewsFromReq[name]!!
            userReviews.sortBy { it.createdAt }
            getMessagesFromReviews(userReviews, userMap[1]).forEach { message ->
                channel.sendMessage(message).await()
            }
        } else if (reviewsFromReq.containsKey(name)) {
            userReviews = reviewsFromReq[name]!!
            userReviews.sortBy { it.createdAt }
            // Сначала старые, потом новые
            messageList.reverse()

            // Заполняем, что не спамить второй раз
            messageList.forEach { message ->
                if (message.embeds.isNotEmpty()) {
                    message.embeds.forEach { embed ->
                        embed.fields.forEach { f -> f.name?.let { reviewIds.add(it) } }
                    }
                }
            }

            val messagesToDelete = mutableListOf<Message>()
            messageList.forEach { message ->
                // Если ревью не осталось, но остались сообщения удаляем их
                if (userReviews.isEmpty()) {
                    messagesToDelete.add(message)
                } else {
                    val limit = userReviews.stream()
                        .limit(Message.MAX_EMBED_COUNT.toLong())
                        .collect(Collectors.toList()).toMutableList()
                    val embeds = limit.map { review -> getMessageEmbedFromReview(review, userMap[1]) }
                    message.editMessage(getMessageWithEmbed(embeds)).await()
                    userReviews.removeAll(limit)

                }
            }

            if (messagesToDelete.isNotEmpty()) BotInstance.deleteMessagesAsync(channel, messagesToDelete)
            // Если сообщений не хватило на все ревью, отправляем новое
            if (userReviews.isNotEmpty()) {
                getMessagesFromReviews(userReviews, userMap[1]).forEach { message ->
                    channel.sendMessage(message).await()
                }
            }

            reviewIds.clear()

        }
    }

    private fun getMessageWithEmbed(embeds: List<MessageEmbed>): Message {
        val messageBuilder = MessageBuilder()
        messageBuilder.setEmbeds(embeds)
        return messageBuilder.build()
    }

    private suspend fun getMessagesFromReviews(userReviews: MutableList<Review>, name: String): MutableList<Message> {
        val embeds = userReviews.map { review -> getMessageEmbedFromReview(review, name) }
        // максимальное количество embed в одном сообщении
        val chunked = embeds.chunked(Message.MAX_EMBED_COUNT)

        return chunked.map { chunk -> getMessageWithEmbed(chunk) }.toMutableList()
    }

    private suspend fun getMessageEmbedFromReview(review: Review, name: String): MessageEmbed {
        val reviewId = review.reviewId.reviewId

        val embedBuilder = EmbedBuilder()
        embedBuilder.setAuthor(
            reviewId,
            "https://codereview.ritperm.rt.ru/${review.reviewId.projectId}/review/$reviewId"
        )
        if (review.numberTask.isNotEmpty()) embedBuilder.setTitle(review.numberTask, review.urlTask)
        embedBuilder.addField(
            MessageEmbed.Field(
                reviewId,
                if (reviewIds.contains(reviewId)) name else getUserLogin()?.let {
                    BotInstance.getUserMention(
                        it
                    )
                },
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
        return embedBuilder.build()
    }

    private fun createCongratulationMessage(): Message {
        val messageBuilder = MessageBuilder("Поздравляю!!!! \n Ты закрыл все ревью")
        return messageBuilder.build()
    }

}