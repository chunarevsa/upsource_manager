package com.rtkit.upsource_manager.events

import com.rtkit.upsource_manager.services.ReviewService
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class InitNewDiscordChannelListener(
    var reviewService: ReviewService,
) : ApplicationListener<UpdatedReviewList> {

    override fun onApplicationEvent(event: UpdatedReviewList) {
        reviewService.updateReviews()
    }
}

class InitNewDiscordChannel(id: String) : ApplicationEvent(id)
