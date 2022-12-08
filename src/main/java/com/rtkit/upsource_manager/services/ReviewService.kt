package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.entities.review.ReviewEntity
import com.rtkit.upsource_manager.payload.api.ReviewsRequest
import com.rtkit.upsource_manager.payload.pacer.review.Participant
import com.rtkit.upsource_manager.payload.pacer.review.Review
import com.rtkit.upsource_manager.repositories.ReviewRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.function.Consumer
import java.util.stream.Collectors

@Service
class ReviewService(
    private val protocolService: ProtocolService,
    private val reviewRepository: ReviewRepository,
    @Value(value = "\${review.defaultTimeToExpired}") val defaultTimeToExpired: Long
) {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    fun loadAllReviews(): MutableList<Review> {
        return protocolService.makeRequest(ReviewsRequest()).getReview()
            ?: throw Exception("не удалось загрузить ревью")
    }

    fun getReviewEntitiesByReviews(reviews: MutableList<Review>): MutableSet<ReviewEntity> {
        return reviews.stream().map { review -> ReviewEntity(review) }.collect(Collectors.toSet())
    }

    fun getOnlyUpdatedReviews(reviewEntities: MutableSet<ReviewEntity>): MutableSet<ReviewEntity> {
        logger.info("review entities from request: ${reviewEntities.size}")

        val reviewEntitiesFromDB = getAllReviews()
        logger.info("review entities from DB: ${reviewEntitiesFromDB.size}")

        reviewEntities.removeAll(reviewEntitiesFromDB)
        return reviewEntities
    }

    private fun getAllReviews(): MutableSet<ReviewEntity> {
        return reviewRepository.findAll().toMutableSet()
    }

    fun saveReviews(reviews: MutableSet<ReviewEntity>): MutableSet<ReviewEntity>? {
        return reviews.stream().map { review -> saveReviews(review) }.collect(Collectors.toSet())
    }

    private fun saveReviews(review: ReviewEntity): ReviewEntity {
        return reviewRepository.save(review)
    }

    fun closeReview() {
        //connectionService.makeRequest(CloseRequest())
    }

    fun getParticipantsFromReview(reviews: MutableSet<ReviewEntity>): MutableSet<ParticipantEntity> {
        val participants: MutableSet<ParticipantEntity> = mutableSetOf()
        reviews.forEach { review -> review.participants.forEach { participant -> participants.add(participant) } }
        return participants
    }


//    var reviews: MutableList<Review> = mutableListOf()
//    var expiredReviewsList: MutableList<Review> = mutableListOf()
//    var participants: MutableMap<String, String> = mutableMapOf()

//    fun getExpiredReviews(filter: String = ""): List<Review> {
//        expiredReviewsList.clear()
//
//        val timeToExpired: Long = if (filter == "") defaultTimeToExpired else {
//            filter.toLong() * 86400000
//        }
//        val expiredDate = Instant.now().minusMillis(timeToExpired)
//
//        reviews.stream().filter { review: Review -> review.state == 1 }.filter { review: Review ->
//                Instant.ofEpochMilli(review.getUpdatedAt().toString().toLong()).isBefore(expiredDate)
//            }.forEach { review: Review -> expiredReviewsList.add(review) }
//
//        return getParticipantName(expiredReviewsList)
//    }

//    private fun getParticipantName(badReviews: MutableList<Review>): List<Review> {
//        badReviews.forEach { review: Review ->
//                review.participants.forEach { participant: Participant ->
//                        participant.name = findUsernameById(participant.userId)
//                    }
//            }
//
//        badReviews.forEach { review: Review ->
//                review.participants.removeIf { participant -> (participant.name == "guest") }
//            }
//        return badReviews
//    }

}

