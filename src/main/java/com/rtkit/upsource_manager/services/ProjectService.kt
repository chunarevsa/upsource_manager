package com.rtkit.upsource_manager.services

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val reviewService: ReviewService
) {
    private val logger: Logger = LogManager.getLogger(ProjectService::class.java)

    @Scheduled(cron = "0 */1 * * * *")
    fun start() {
        logger.info("================== Start init ProjectService ==================")
        val reviews = reviewService.getReviewsEntityByReviews(reviewService.loadAllReviews())

        val onlyUpdatedReviews = reviewService.getOnlyUpdatedReviews(reviews)
        reviewService.saveReviews(onlyUpdatedReviews)
        logger.info("================== ${onlyUpdatedReviews.size} reviews have been updated ================== \n")

//        getExpiredReviews()
//        logger.info("================== ${expiredReviewsList.size} Expired reviews have been updated ==================")
//
//        reviewService.updateData()

    }
}