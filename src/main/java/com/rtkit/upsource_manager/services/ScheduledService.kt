package com.rtkit.upsource_manager.services

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduledService(
    private val reviewService: ReviewService,
    private val userService: UserService,
) {
    private val logger: Logger = LogManager.getLogger(ScheduledService::class.java)

    init {
        updateUsers()
        updateReviews()
    }

    @Scheduled(cron = "0 */30 * * * *")
    fun updateReviews() {
        logger.info("==== Start update reviews")
        reviewService.updateReviews(query = "state:open")
        logger.info("==== End update reviews\n")
    }

//    @Scheduled(cron = "0 */60 * * * *")
    fun updateUsers() {
        logger.info("==== Start update developers")
        userService.updateUsers()
        logger.info("==== End update developers\n")
    }

    /**
     * Проверка на пустые ревью
     */
//    @Scheduled(cron = "0 */55 * * * *")
    fun closeReviewsWithEmptyRevision() {
        logger.info("==== Start close reviews with empty revision")
        reviewService.closeReviewsWithEmptyRevision()
        logger.info("==== End close reviews with empty revision\n")
    }

}