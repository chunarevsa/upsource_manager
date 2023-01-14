package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.upsource.review.CloseReviewRequestDTO
import com.rtkit.upsource_manager.payload.upsource.review.Review
import com.rtkit.upsource_manager.payload.upsource.review.ReviewId
import com.rtkit.upsource_manager.payload.upsource.review.ReviewsRequestDTO
import com.rtkit.upsource_manager.payload.upsource.revision.ReviewSummaryChangesRequestDTO
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ReviewService(
    private val protocolService: ProtocolService,
    @Value(value = "\${review.updatedAt.dayToExpired}") val dayToExpired: Int,
    @Value(value = "\${review.createdAt.dayToExpired}") val createdAtExpired: Int,
    private val appEventPublisher: ApplicationEventPublisher
) {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    private var reviews = mutableListOf<Review>()
    private var count: Int = 0

    /**
     * Храним просроченные для статистики
     */
    private var expiredAndClosedReviewIds = mutableSetOf<ReviewId>()

    fun updateReviews(limit: Int = 2000, sortBy: String = "updated") {
        reviews.clear()
        val reviewsFromRequest = protocolService.makeRequest(
            ReviewsRequestDTO(
                limit = limit,
                sortBy = sortBy
            )
        ).reviews

        // Вывод общего количества закрытых ревью за всё время
        reviewsFromRequest.forEach { review -> if (review.state == 2) count++ }
        logger.info("======= Количество закрытых: $count ===========")
        count = 0

        val now = Instant.now().toEpochMilli()
        reviewsFromRequest
            .filter { review -> review.state == 1 }
            .forEach { review ->
                if (now - review.updatedAt > getEpochMilliFromDay(dayToExpired) ||
                    now - review.createdAt > getEpochMilliFromDay(createdAtExpired)
                ) closeExpiredReview(review) else reviews.add(review)
            }
        logger.info("======= Количество активных ревью: ${reviews.size} ===========")
    }


    private fun closeExpiredReview(review: Review) {

//        appEventPublisher.publishEvent(FindExpiredReview(review))
        expiredAndClosedReviewIds.add(review.reviewId)
        closeReview(review)
    }

    private fun closeReview(review: Review) {
        val req = CloseReviewRequestDTO(reviewId = review.reviewId)
        // Удаляем в Upsource
        if (protocolService.makeRequest(req).isNotSuccessful()) {
            logger.error("Не удалось удалить ревью ${review.reviewId}")
            throw Exception("Ошибка закрытия ревью ${review.reviewId}")
        }
        logger.info("========== Закрыли ревью: ${review.reviewId} ==========")
        // Удаляем у нас
        deleteReview(review)
    }

    private fun deleteReview(review: Review) {
        reviews.find { r -> r.reviewId.reviewId == review.reviewId.reviewId }
    }

    fun closeReviewsWithEmptyRevision() {
        updateReviews()
        getReviewsWithEmptyRevision().forEach { review -> closeReview(review) }
    }

    private fun getReviewsWithEmptyRevision(): MutableList<Review> {
        val reviewsWithEmptyRevisionList = mutableListOf<Review>()
        reviews.stream()
            .filter { review -> review.state == 1 }
            .filter { review -> revisionIsEmpty(review) }
            .forEach { review -> reviewsWithEmptyRevisionList.add(review) }

        return reviewsWithEmptyRevisionList
    }

    private fun revisionIsEmpty(review: Review): Boolean {
        return try {
            val resp = protocolService.makeRequest(ReviewSummaryChangesRequestDTO(reviewId = review.reviewId))
            if (resp.annotation != null && resp.annotation == "Review does not contain any revisions.") {
                logger.info("Review does not contain any revisions: ${review.reviewId}")
                true
            } else false
        } catch (e: Exception) {
            logger.info("Не удалось получить ответ ReviewSummaryChangesResponseDTO у ревью ${review.reviewId}")
            false
        }
    }

    private fun getEpochMilliFromDay(day: Int): Long {
        return day * 86400000L
    }

}

