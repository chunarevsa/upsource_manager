package com.rtkit.upsource_manager.payload.api

import com.rtkit.upsource_manager.payload.pacer.review.Review
import com.rtkit.upsource_manager.payload.pacer.review.ReviewRoot

class ReviewResponse(
    response: String
) : ABaseUpsourceResponse(response) {

    private val reviewRootObj = objectMapper.readValue(result, ReviewRoot::class.java)

    fun getReview(): MutableList<Review>? {
        return reviewRootObj.getResult().getReviews()
    }
}

