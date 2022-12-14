package com.rtkit.upsource_manager.services

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val reviewService: ReviewService,
    private val userService: UserService
) {
    private val logger: Logger = LogManager.getLogger(ProjectService::class.java)

    @Scheduled(cron = "0 */1 * * * *")
    fun update() {
        logger.info("================== Start init ProjectService ==================")
        userService.updateUsers()
        reviewService.updateReviews(2000)
        logger.info("================== End init ProjectService ==================\n")
    }

    //@Scheduled(cron = "0 */100 * * * *")
    fun updateReviews() {
        logger.info("================== Start update reviews ==================")
        // Можно сделать выгрузку из application.properties и сортировку по дате создания
        val limit: Int = 2000
        reviewService.updateReviews(limit)
        logger.info("================== End update reviews ==================\n")
    }

    //@Scheduled(cron = "0 */300 * * * *")
    fun updateUsers() {
        logger.info("================== Start update developers ==================")
        userService.updateUsers()
        logger.info("================== End update developers ==================\n")
    }

    /**
     * Раз в 30 дней
     */
    @Scheduled(cron = "0 */10 * * * *")
    fun closeReviewsWithEmptyRevision() {
        logger.info("================== Start close reviews with empty revision ==================")
        reviewService.closeReviewsWithEmptyRevision()
        logger.info("================== End close reviews with empty revision ==================\n")
    }

}