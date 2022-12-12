package com.rtkit.upsource_manager.services

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val reviewService: ReviewService,
    private val developerService: DeveloperService
) {
    private val logger: Logger = LogManager.getLogger(ProjectService::class.java)

    @Scheduled(cron = "0 */1 * * * *")
    fun update() {
        logger.info("================== Start init ProjectService ==================")
        developerService.updateDevelopers()
        reviewService.updateReviews()
        logger.info("\n================== Start init ProjectService ==================")
    }

    //@Scheduled(cron = "0 */100 * * * *")
    fun updateReviews() {
        logger.info("================== Start update reviews ==================")
        reviewService.updateReviews()
        logger.info("\n================== End update reviews ==================")
    }

    //@Scheduled(cron = "0 */300 * * * *")
    fun updateDevelopers() {
        logger.info("================== Start update developers ==================")
        developerService.updateDevelopers()
        logger.info("\n================== End update developers ==================")
    }


}