package com.rtkit.upsource_manager.events

import com.rtkit.upsource_manager.payload.upsource.review.Review
import com.rtkit.upsource_manager.services.BotService
import com.rtkit.upsource_manager.services.ReviewService
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class UpdatedReviewListListener(
    var reviewService: ReviewService,
    var botService: BotService,
) : ApplicationListener<UpdatedReviewList> {

    override fun onApplicationEvent(event: UpdatedReviewList) {
        val sortedByLogin = reviewService.sortedByLogin(event.reviews)
        botService.updatedReviewsMessage(sortedByLogin)

    }
}

class UpdatedReviewList(var reviews: MutableList<Review>) : ApplicationEvent(reviews)
