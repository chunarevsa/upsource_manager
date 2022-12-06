package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.api.ReviewsRequest
import com.rtkit.upsource_manager.payload.pacer.review.Review
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val protocolService: ProtocolService,
    @Value(value = "\${review.defaultTimeToExpired}")
    val defaultTimeToExpired: Long
) {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    var reviews: MutableList<Review>? = mutableListOf()

    init {
        loadAllReviews()
    }

    @Scheduled(cron = "0 */10 * * * *")
    fun loadAllReviews() {
        reviews =
            protocolService.makeRequest(ReviewsRequest()).getReview() ?: throw Exception("не удалось загрузить ревью")
        logger.info("================== ${reviews!!.size} reviews have been uploaded ==================")
    }

    fun closeReview() {
        //connectionService.makeRequest(CloseRequest())
    }
}

