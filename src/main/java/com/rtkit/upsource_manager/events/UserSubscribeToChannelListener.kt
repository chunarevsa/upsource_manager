package com.rtkit.upsource_manager.events

import com.rtkit.upsource_manager.services.ReviewService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

/**
 * Запускает полный апдейт при подписки пользователя
 */
@Component
class UserSubscribeToChannelListener(
    private val reviewService: ReviewService
) : ApplicationListener<UserSubscribeToChannel> {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    override fun onApplicationEvent(event: UserSubscribeToChannel) {
        logger.info("==== Пользователь ${event.userId} подписался на канал")
        reviewService.updateReviews(query = "state:open")
    }
}

class UserSubscribeToChannel(var userId: String) : ApplicationEvent(userId)