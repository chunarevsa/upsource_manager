package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.pacer.review.Review
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val connectionService: ConnectionService
) {
    var reviews: MutableList<Review>? = mutableListOf()


    fun loadAllReviews() {
        reviews = connectionService.makeRequest(ReviewsRequest()).getReview()

        println("========= reviews.size =${reviews?.size} ===============")

    }

    fun closeReview() {
        //connectionService.makeRequest(CloseRequest())
    }
}

