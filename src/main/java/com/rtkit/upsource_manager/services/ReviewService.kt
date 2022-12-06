package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.api.ReviewsRequest
import com.rtkit.upsource_manager.payload.api.UserRequest
import com.rtkit.upsource_manager.payload.pacer.review.Participant
import com.rtkit.upsource_manager.payload.pacer.review.Review
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ReviewService(
    private val protocolService: ProtocolService,
    @Value(value = "\${review.defaultTimeToExpired}")
    val defaultTimeToExpired: Long
) {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    var reviews: MutableList<Review> = mutableListOf()
    var expiredReviewsList: MutableList<Review> = mutableListOf()
    var participants: MutableMap<String, String> = mutableMapOf()

    init {
        updateData()
    }

    fun loadAllReviews() {
        reviews =
            protocolService.makeRequest(ReviewsRequest()).getReview() ?: throw Exception("не удалось загрузить ревью")
    }

    @Scheduled(cron = "0 */10 * * * *")
    fun updateData() {
        reviews.clear()
        loadAllReviews()
        logger.info("================== ${reviews.size} reviews have been updated ==================")

        expiredReviewsList.clear()
        getExpiredReviews()
        logger.info("================== ${expiredReviewsList.size} Expired reviews have been updated ==================")
    }

    fun getExpiredReviews(filter: String = ""): List<Review> {
        expiredReviewsList.clear()

        val timeToExpired: Long = if (filter == "") defaultTimeToExpired else {
            filter.toLong() * 86400000
        }
        val expiredDate = Instant.now().minusMillis(timeToExpired)

        reviews
            .stream()
            .filter { review: Review -> review.state == 1 }
            .filter { review: Review ->
                Instant.ofEpochMilli(review.getUpdatedAt().toString().toLong()).isBefore(expiredDate)
            }
            .forEach { review: Review -> expiredReviewsList.add(review) }

        return getParticipantName(expiredReviewsList)
    }

    private fun getParticipantName(badReviews: MutableList<Review>): List<Review> {
        badReviews
            .forEach { review: Review ->
                review.participants
                    .forEach { participant: Participant -> participant.name = findUsernameById(participant.userId) }
            }

        badReviews
            .forEach { review: Review ->
                review.participants.removeIf { participant -> (participant.name == "guest") }
            }
        return badReviews
    }

    private fun findUsernameById(userId: String): String? {
        if (participants[userId] != null) return participants[userId].toString()

        val participant = protocolService.makeRequest(UserRequest(userId)).getParticipant()

        participants[userId] = participant.name
        return participants[userId]
    }

    fun closeReview() {
        //connectionService.makeRequest(CloseRequest())
    }
}

