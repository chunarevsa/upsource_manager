package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.review.ReviewEntity
import com.rtkit.upsource_manager.payload.pacer.review.Participant
import com.rtkit.upsource_manager.payload.pacer.review.Review
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val reviewService: ReviewService,
    private val participantService: ParticipantService,
    private val developerService: DeveloperService
) {
    private val logger: Logger = LogManager.getLogger(ProjectService::class.java)

    @Scheduled(cron = "0 */1 * * * *")
    fun start() {
        logger.info("================== Start init ProjectService ==================")
        val reviews: MutableList<Review> = reviewService.loadAllReviews()
        val reviewEntities: MutableSet<ReviewEntity>  = reviewService.getReviewEntitiesByReviews(reviews)
        val onlyUpdatedReviews = reviewService.getOnlyUpdatedReviews(reviewEntities)

        val participants = reviewService.getParticipantsFromReview(onlyUpdatedReviews)
        developerService.findNewDeveloperFromParticipants(participants)

        participantService.saveParticipants(participants)
        reviewService.saveReviews(onlyUpdatedReviews)
        logger.info("================== ${onlyUpdatedReviews.size} reviews have been updated ================== \n")

//        getExpiredReviews()
//        logger.info("================== ${expiredReviewsList.size} Expired reviews have been updated ==================")
//
//        reviewService.updateData()

    }
}