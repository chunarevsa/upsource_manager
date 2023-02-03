package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.ChannelStorage
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.await
import com.rtkit.upsource_manager.payload.upsource.review.EReviewExpiredStatus
import com.rtkit.upsource_manager.payload.upsource.review.Review
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

    /** Максимальная ширина одного блока в сообщении */
    var maxEmbedSize: Int = 35

    suspend fun initializeChannel(): BotChannelHolder {
        Config.channelStorage.computeIfAbsent(channel.id) { ChannelStorage() }
        deleteMessage()
        createIntroMessage()
        logger.info("==== Channel ${channel.name} initialization finishing...")
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
                logger.info("==== Channel ${channel.name} creates intro message")
            }
            introMessage!!.contentRaw != Config.introMessage -> {
                val it = introMessage!!.editMessage(MessageBuilder(Config.introMessage).build()).await()
                introMessage = it
                Config.channelStorage[channel.id]!!.introId = it.id
                logger.info("==== Channel ${channel.name} edit intro message")
            }
            else -> {
                logger.info("==== Channel ${channel.name} intro message OK")
            }
        }
    }

    suspend fun updateReviewMessages(reviewsFromReq: MutableMap<String, MutableList<Review>>) {
        if (Config.channelStorage[channel.id]!!.user.isEmpty()) // TODO: сделать ошибку
            throw Exception("Канал без пользователя. Название:${channel.name}, id:${channel.id}")

        val userMap = Config.getUserMapByChannelId(channel.id)
        val upsourceLogin = userMap.upsourceLogin
        val userReviews: MutableList<Review>
        // Не трогаем интро
        val messageList = MessageHistory.getHistoryFromBeginning(channel).await().retrievedHistory.toMutableList()
        messageList.removeIf { it.id == Config.channelStorage[channel.id]!!.introId }

        if (!reviewsFromReq.containsKey(upsourceLogin)) {
            // Нет ревью на этом пользователе, заполняем первое сообщение поздравлением, остальные удаляем
            if (messageList.isNotEmpty()) {
                messageList[0].editMessage(createCongratulationMessage())
                messageList.removeAt(0)
                if (messageList.isNotEmpty()) BotInstance.deleteMessagesAsync(channel, messageList)
            } else channel.sendMessage(createCongratulationMessage()).await()

        } else if (messageList.isEmpty() && reviewsFromReq.containsKey(upsourceLogin)) {
            // Нет сообщений, но есть ревью
            userReviews = reviewsFromReq[upsourceLogin]!!
            userReviews.sortBy { it.createdAt }
            getMessagesFromReviews(userReviews).forEach { message ->
                channel.sendMessage(message).await()
            }
        } else if (reviewsFromReq.containsKey(upsourceLogin)) {
            userReviews = reviewsFromReq[upsourceLogin]!!
            userReviews.sortBy { it.createdAt }
            // Сначала старые, потом новые
            messageList.reverse()

            // Заполняем, что не спамить второй раз
            messageList.forEach { message ->
                if (message.embeds.isNotEmpty()) {
                    message.embeds.forEach { embed ->
                        embed.author?.name?.let { reviewIds.add(it) }
                    }
                }
            }

            val messagesToDelete = mutableListOf<Message>()
            messageList.forEach { message ->
                // Если ревью не осталось, но остались сообщения удаляем их
                if (userReviews.isEmpty()) {
                    messagesToDelete.add(message)
                } else {
                    val review = userReviews.first()
                    message.editMessage(getMessageFromReview(review))
                    userReviews.remove(review)
                }
            }

            if (messagesToDelete.isNotEmpty()) BotInstance.deleteMessagesAsync(channel, messagesToDelete)
            // Если сообщений не хватило на все ревью, отправляем новое
            if (userReviews.isNotEmpty()) {
                getMessagesFromReviews(userReviews).forEach { message ->
                    channel.sendMessage(message).await()
                }
            }
            reviewIds.clear()
        }
    }

    private fun getMessagesFromReviews(userReviews: MutableList<Review>): MutableList<Message> {
        return userReviews.map { review -> getMessageFromReview(review) }.toMutableList()
    }

    /** Одно сообщение - один блок - одно ревью */
    private fun getMessageFromReview(review: Review): Message {
        val messageBuilder = MessageBuilder()
        messageBuilder.setEmbeds(getEmbedFromReview(review))
        return messageBuilder.build()
    }

    // Построение блоков можно посмотреть здесь https://autocode.com/tools/discord/embed-builder/
    private fun getEmbedFromReview(review: Review): MessageEmbed {
        val reviewId = review.reviewId.reviewId

        // Для выравнивания заполняем прозрачными пробелами (они шире в 2 раза чем обычный)
        var author = if (review.author.isNullOrBlank()) "Неизвестно⠀⠀⠀⠀⠀⠀⠀⠀" else review.author
        author = author.padEnd(maxEmbedSize - (maxEmbedSize - author.length) / 2, '⠀')

        val embedBuilder = EmbedBuilder()
        // Номер ревью в Upsource
        embedBuilder.setAuthor(
            reviewId,
            "https://codereview.ritperm.rt.ru/${review.reviewId.projectId}/review/$reviewId"
        )

        // Номер таски из жиры, если было в комментарии коммита
        if (review.numberTask.isNotEmpty()) embedBuilder.setTitle(review.numberTask, review.urlTask)

        // Упоминание если новое ревью и смогли смапить юзера
        if (!reviewIds.contains(reviewId)) {
            Config.userMap
            embedBuilder.setDescription(Config.getUserMapByChannelId(channel.id).discordUserMention)
        }

        // Осталось дней
        if (review.daysToExpired == 0) {
            embedBuilder.addField(
                MessageEmbed.Field(
                    "Осталось часов",
                    (review.millisToDeadline / 3600000).toString(),
                    true,
                    true
                )
            )
        } else {
            embedBuilder.addField(
                MessageEmbed.Field(
                    "Осталось дней",
                    "${review.daysToExpired}",
                    true,
                    true
                )
            )
        }

        // Отступ
        embedBuilder.addBlankField(true)

        // Автор коммита
        embedBuilder.addField(
            MessageEmbed.Field(
                "Автор коммита",
                author,
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