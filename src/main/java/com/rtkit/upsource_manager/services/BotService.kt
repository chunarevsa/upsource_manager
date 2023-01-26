package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.channel.BotChannelHolderManager
import com.rtkit.upsource_manager.payload.upsource.review.Review
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Service
class BotService {
    private val logger: Logger = LogManager.getLogger(BotService::class.java)

    private val lock = ReentrantLock()

    init {
        try {
            logger.info("Server starting ...")
            Config.load()
            logger.info("... Config loaded")

            BotInstance.runBot()
            BotInstance.log("SERVICE STARTED")
        } catch (t: Throwable) {
            logger.error("", t)
        }
    }

    fun updatedReviewsMessage(reviews: MutableMap<String, MutableList<Review>>) {
        lock.withLock {
            runBlocking {
                launch {
                    BotChannelHolderManager.updateReviewMessages(reviews)
                }
            }
        }
    }

}