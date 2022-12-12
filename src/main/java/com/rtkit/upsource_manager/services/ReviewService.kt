package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.entities.review.ReviewEntity
import com.rtkit.upsource_manager.payload.api.request.ReviewsRequestDTO
import com.rtkit.upsource_manager.repositories.ReviewRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ReviewService(
    private val protocolService: ProtocolService,
    private val reviewRepository: ReviewRepository,
    private val participantService: ParticipantService,
    @Value(value = "\${review.defaultTimeToExpired}") val defaultTimeToExpired: Long
) {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    fun updateReviews() {
        val reviewEntitiesFromDB = findAll()
        val reviewsFromRequest = protocolService.makeRequest(ReviewsRequestDTO(limit = 20))?.result?.reviews
            ?: throw Exception("не удалось загрузить ревью")

        // Из Review -> ReviewEntity, Participant -> ParticipantEntity
        val reviewEntities: MutableSet<ReviewEntity> = reviewsFromRequest.stream().map { review ->
            ReviewEntity(review).apply {
                review.participants.forEach { participant ->
                    this.participants.add(
                        ParticipantEntity(participant)
                    )
                }
            }
        }.collect(Collectors.toSet())

        reviewEntities.removeAll(reviewEntitiesFromDB)
        reviewEntities.forEach { reviewEntity -> participantService.saveParticipants(reviewEntity.participants) }
        saveReviews(reviewEntities)
        logger.info("========== Обновлено ${reviewEntities.size} ревью ==========")

    }

    private fun findAll(): MutableSet<ReviewEntity> {
        return reviewRepository.findAll().toMutableSet()
    }


    private fun saveReviews(reviews: MutableSet<ReviewEntity>): MutableSet<ReviewEntity>? {
        return reviews.stream().map { review -> saveReview(review) }.collect(Collectors.toSet())
    }

    private fun saveReview(review: ReviewEntity): ReviewEntity {
        return reviewRepository.save(review)
    }

    fun closeReview() {
        //connectionService.makeRequest(CloseRequest())
    }


}

