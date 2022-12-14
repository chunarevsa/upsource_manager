package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.entities.review.ReviewEntity
import com.rtkit.upsource_manager.payload.api.review.CloseReviewRequestDTO
import com.rtkit.upsource_manager.payload.api.review.ReviewId
import com.rtkit.upsource_manager.payload.api.review.ReviewsRequestDTO
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

    fun updateReviews(limit: Int, sortBy: String = "id,desc") {
        val reviewEntitiesFromDB = findAll()
        val reviewsFromRequest = protocolService.makeRequest(
            ReviewsRequestDTO(
                limit = limit,
                sortBy = sortBy
            )
        )?.reviews ?: throw Exception("не удалось загрузить ревью")

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
        reviewEntities.filter { reviewEntity -> !reviewEntity.isRemoved }
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
        return try {
            reviewRepository.save(review)
        } catch (e: Exception) {
            logger.error("==== Не удалось сохранить ревью: $review")
            throw Exception() // TODO: сделать обработку без бросания, можно выше отфильтровывать
        }

    }

    fun closeReviews(reviewList: MutableSet<ReviewEntity>) {
        reviewList.forEach { review -> closeReview(review.upsourceId) }
    }

    fun closeReview(upsourceId: String) {
        val req = CloseReviewRequestDTO(ReviewId().apply {
            this.reviewId = upsourceId
        })
        // Удаляем в Upsource
        if (!protocolService.makeRequest(req).isSuccessfull()) throw Exception()

        // Удаляем у нас
        deleteReviewByUpsourceId(upsourceId)
    }

    private fun getReviewByUpsourceId(upsourceId: String): ReviewEntity {
        return reviewRepository.findByUpsourceId(upsourceId)
    }

    private fun deleteReviewByUpsourceId(upsourceId: String) {
        return try {
            reviewRepository.deleteByUpsourceId(upsourceId)
        } catch (e: Exception) {
            logger.error("Не удалось удалить ревью: $upsourceId: String")
        }
    }

//    private fun getReviewsWithEmptyRevision(): List<Review> {
//        reviewsWithEmptyRevisionList.clear()
//        reviews.stream()
//                .filter { review: Review -> review.state == 1 }
//                .filter { review: Review -> revisionIsEmpty(review) }
//                .forEach { review: Review -> reviewsWithEmptyRevisionList.add(review) }
//
//        return getParticipantName(reviewsWithEmptyRevisionList)
//    }
//
//    private fun revisionIsEmpty(review: Review): Boolean {
//        val con = authService.getConnection(RequestURL.GET_REVIEWS)
//        val jsonRequest = "{\"reviewId\": {\"projectId\": \"elk\", \"reviewId\":\"${review.reviewId.reviewId}\"}}"
//        val response = authService.doPostRequestAndReceiveResponse(con, jsonRequest)
//
//        val revisionRootObj = REVISION_MAPPER.readValue(response, ChangesRoot::class.java)
//        //у пустых ревью есть аннотация "Review does not contain any revisions."
//        return (revisionRootObj.result.getAnnotation() != null)
//    }
//
//


}

