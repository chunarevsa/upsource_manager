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
        reviewService.updateReviews(2000)
        logger.info("================== End init ProjectService ==================")
    }

    //@Scheduled(cron = "0 */100 * * * *")
    fun updateReviews() {
        logger.info("================== Start update reviews ==================")
        // Можно сделать выгрузку из application.properties и сортировку по дате создания
        val limit: Int = 2000
        val sortBy: String = "id,desc"
        reviewService.updateReviews(limit, sortBy)
        logger.info("================== End update reviews ==================")
    }

    //@Scheduled(cron = "0 */300 * * * *")
    fun updateDevelopers() {
        logger.info("================== Start update developers ==================")
        developerService.updateDevelopers()
        logger.info("================== End update developers ==================")
    }


}