package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.config.BotConfig
import com.rtkit.upsource_manager.events.bot.ReadyEventListener
import com.rtkit.upsource_manager.events.upsource.FindExpiredReview
import com.rtkit.upsource_manager.payload.upsource.review.Review
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.Compression
import net.dv8tion.jda.internal.entities.EntityBuilder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class BotService(
    private val appEventPublisher: ApplicationEventPublisher
) {
    private val logger: Logger = LogManager.getLogger(BotService::class.java)

    var jdaInstance: JDA? = null
        private set

    init {
        val builder = JDABuilder.createDefault(BotConfig.BOT_TOKEN)
        builder.setCompression(Compression.ZLIB)
        builder.setActivity(
            EntityBuilder.createActivity(
                "Запуск Диcкорд бота ",
                null,
                Activity.ActivityType.CUSTOM_STATUS
            )
        )
        builder.addEventListeners(ReadyEventListener())

        logger.info("Connecting to Discord...")
        jdaInstance = builder.build().awaitReady()
        logger.info("Bot started")

    }

    fun onTick() {
        if (jdaInstance != null) logger.info("One Tick")
        appEventPublisher.publishEvent(FindExpiredReview(Review()))
    }

    fun stopBot() {
        if (jdaInstance != null) {
            try {
                //log("SERVICE SHUTDOWN") // TODO:
                jdaInstance!!.shutdown()
            } catch (t: Throwable) {
                logger.error("Ошибка при остановки бота", t)
            }
        }
    }

    fun notifyUser() {
        logger.info("Уведомляем пользователя что ревью уже закрылось по просроку")
    }
}