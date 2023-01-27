package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.events.UpdatedReviewList
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
    private val userService: UserService,
    @Value(value = "\${review.createdAt.dayToExpired}") val createdAtExpired: Int,
    private val appEventPublisher: ApplicationEventPublisher
) {
    private val logger: Logger = LogManager.getLogger(ReviewService::class.java)

    /** Список ревью */
    private var reviews = mutableListOf<Review>()

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

        val now = Instant.now().toEpochMilli()
        reviewsFromRequest
            .filter { review -> review.state == 1 }
            .forEach { review ->
                if (now - review.createdAt > getEpochMilliFromDay(createdAtExpired)) closeExpiredReview(review) else {
                    reviews.add(makeCompletedReview(review))
                }
            }
        appEventPublisher.publishEvent(UpdatedReviewList(reviews))
        logger.info("======= Количество активных ревью: ${reviews.size} ===========")
    }

    private fun makeCompletedReview(review: Review): Review {
        return setTask(setDaysToExpired(review))
    }

    private fun setTask(review: Review): Review {
        val title = review.title
        val prefix = "https://ihelp.rt.ru/browse/ELKDEV-"

        if (title.isNullOrBlank() || !title.startsWith(prefix)) {
            return review
        }

        val number = title.drop(prefix.length).padEnd(6, ' ').substring(0, 6).filter { it.isDigit() }
        review.urlTask = prefix + number
        review.numberTask = "ELKDEV-$number"
        return review
    }

    private fun setDaysToExpired(review: Review): Review {
        val deadline = review.createdAt + getEpochMilliFromDay(createdAtExpired)
        val millisToDeadline = deadline - Instant.now().toEpochMilli()
        var daysToDeadline = (millisToDeadline / 86400000).toInt()
        if (daysToDeadline < 0) daysToDeadline = 0

        review.daysToExpired = daysToDeadline
        review.expiredStatus = when {
            daysToDeadline > 10 -> EReviewExpiredStatus.FRESH
            daysToDeadline in 6..10 -> EReviewExpiredStatus.ATTENTION
            daysToDeadline in 1..5 -> EReviewExpiredStatus.FIRE
            daysToDeadline < 0 -> EReviewExpiredStatus.EXPIRED
            else -> {
                logger.error("Ошибка при определении просроченности ревью: $review")
                EReviewExpiredStatus.EXPIRED
            }
        }
        return review
    }


    private fun closeExpiredReview(review: Review) {
//        appEventPublisher.publishEvent(FindExpiredReview(review)) // TODO: событие FindExpiredReview
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


    fun sortedByLogin(reviews: MutableList<Review>): MutableMap<String, MutableList<Review>> {
        val reviewMapping = mutableMapOf<String, MutableList<Review>>()
        reviews.forEach { review ->
            review.participants.forEach { participant ->
                // отсеять владельцев кода от ревьюверов
                val login = userService.getLoginByUserId(participant.userId)
                val isReviewer: Boolean = participant.role == 2
                if (login != null && isReviewer) {
                    val container = reviewMapping.computeIfAbsent(login, { mutableListOf<Review>() })
                    container.add(review)
                }
            }
        }
        return reviewMapping
    }
}

