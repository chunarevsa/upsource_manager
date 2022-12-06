package com.rtkit.upsource_manager.services

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service //TODO: Переделать в Joby
class ProjectService(
    private val reviewService: ReviewService
) {

    private val logger: Logger = LogManager.getLogger(ProjectService::class.java)

    fun start() {
        logger.info("Start init ProjectService")
        reviewService.loadAllReviews()

    }
}