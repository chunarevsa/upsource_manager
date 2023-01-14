package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.Config
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class BotService {
    private val logger: Logger = LogManager.getLogger(BotService::class.java)
    
    init {
        try {
            logger.info("Server starting ...")

            Config.load()
            logger.info("... Config loaded")

//            packages("ru.dartit.discordbot.handling", "ru.dartit.discordbot.handler")
//            register(CustomJsonProvider())
//            register(ApiRequestContextBinder())
//            register(RequestFilter())
            logger.info("... Jersey initialized")

            BotInstance.runBot()
            BotInstance.log("SERVICE STARTED")
        } catch (t: Throwable) {
            logger.error("", t)
        }
    }
}