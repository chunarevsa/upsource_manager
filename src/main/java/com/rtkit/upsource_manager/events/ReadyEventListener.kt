package com.rtkit.upsource_manager.events

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ReadyEventListener : EventListener {
    private val logger: Logger = LogManager.getLogger(ReadyEventListener::class.java)

    override fun onEvent(event: GenericEvent) {
        if (event is ReadyEvent) {
            logger.info("The bot is ready")
        }
    }

}