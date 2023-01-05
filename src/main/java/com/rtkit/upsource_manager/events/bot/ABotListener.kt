package com.rtkit.upsource_manager.events.bot

import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
abstract class ABotListener : ListenerAdapter() {
    val logger: Logger = LogManager.getLogger(ABotListener::class.java)
}
