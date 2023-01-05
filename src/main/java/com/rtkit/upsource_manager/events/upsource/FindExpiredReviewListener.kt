package com.rtkit.upsource_manager.events.upsource

import com.rtkit.upsource_manager.payload.upsource.review.Review
import com.rtkit.upsource_manager.services.BotService
import org.springframework.stereotype.Component

@Component
class FindExpiredReviewListener(
    private val botService: BotService
) : AUpsourceListener<FindExpiredReview>() {

    override fun onApplicationEvent(event: FindExpiredReview) {
        logger.info("========== Нашли просроченное ревью: ${event.review.reviewId}")
        botService.notifyUser()

    }
}

class FindExpiredReview(
    var review: Review
) : AUpsourceEvent(review)

