package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.review.ReviewEntity
import com.rtkit.upsource_manager.payload.api.FullUserInfoDTO
import com.rtkit.upsource_manager.payload.api.ReviewsRequest
import com.rtkit.upsource_manager.payload.pacer.review.Participant
import com.rtkit.upsource_manager.payload.pacer.review.Review
import com.rtkit.upsource_manager.repositories.ReviewRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.stream.Collectors

@Service
class ReviewService(
    private val protocolService: ProtocolService,
    private val reviewRepository: ReviewRepository,
    @Value(value = "\${review.defaultTimeToExpired}") val defaultTimeToExpired: Long
) {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    var reviews: MutableList<Review> = mutableListOf()
    var expiredReviewsList: MutableList<Review> = mutableListOf()
    var participants: MutableMap<String, String> = mutableMapOf()

    fun loadAllReviews(): MutableList<Review> {
        return protocolService.makeRequest(ReviewsRequest()).getReview()
            ?: throw Exception("не удалось загрузить ревью")
    }

    fun getReviewsEntityByReviews(reviews: MutableList<Review>): MutableSet<ReviewEntity> {
        val part: MutableSet<Participant> = mutableSetOf()
        reviews.forEach { review: Review ->
            review.participants.forEach { participant: Participant ->
                part.add(participant)
            }
        }

        logger.info("====")


        return reviews.stream().map { review -> ReviewEntity(review) }.collect(Collectors.toSet())
    }

    fun getExpiredReviews(filter: String = ""): List<Review> {
        expiredReviewsList.clear()

        val timeToExpired: Long = if (filter == "") defaultTimeToExpired else {
            filter.toLong() * 86400000
        }
        val expiredDate = Instant.now().minusMillis(timeToExpired)

        reviews.stream().filter { review: Review -> review.state == 1 }.filter { review: Review ->
                Instant.ofEpochMilli(review.getUpdatedAt().toString().toLong()).isBefore(expiredDate)
            }.forEach { review: Review -> expiredReviewsList.add(review) }

        return getParticipantName(expiredReviewsList)
    }

    private fun getParticipantName(badReviews: MutableList<Review>): List<Review> {
        badReviews.forEach { review: Review ->
                review.participants.forEach { participant: Participant ->
                        participant.name = findUsernameById(participant.userId)
                    }
            }

        badReviews.forEach { review: Review ->
                review.participants.removeIf { participant -> (participant.name == "guest") }
            }
        return badReviews
    }

    private fun findUsernameById(userId: String): String? {
        if (participants[userId] != null) return participants[userId].toString()

        val participant = protocolService.makeRequest(FullUserInfoDTO(userId)).getFirstParticipant()

        participants[userId] = participant.name
        return participants[userId]
    }

    fun closeReview() {
        //connectionService.makeRequest(CloseRequest())
    }

    fun getOnlyUpdatedReviews(reviewsFromRequest: MutableSet<ReviewEntity>): MutableSet<ReviewEntity> {
        logger.info("reviews from request: ${reviewsFromRequest.size}")

        val reviewsFromDB = getAllReviews()
        logger.info("reviews from DB: ${reviewsFromDB.size}")

        reviewsFromRequest.removeAll(reviewsFromDB)
        return reviewsFromRequest
    }

    private fun getAllReviews(): MutableSet<ReviewEntity> {
        return reviewRepository.findAll().toMutableSet()
    }

    fun saveReviews(reviews: MutableSet<ReviewEntity>): MutableSet<ReviewEntity>? {
        reviews.forEach { review -> saveReviews(review) }
        return reviews.stream().map { review -> saveReviews(review) }.collect(Collectors.toSet())
    }

    private fun saveReviews(review: ReviewEntity): ReviewEntity {
        return reviewRepository.save(review)
    }

}

