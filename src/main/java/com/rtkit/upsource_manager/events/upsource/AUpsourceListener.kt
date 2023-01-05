package com.rtkit.upsource_manager.events.upsource

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
abstract class AUpsourceListener<EVENT : AUpsourceEvent> : ApplicationListener<EVENT> {
    val logger: Logger = LogManager.getLogger(AUpsourceListener::class.java)
}

abstract class AUpsourceEvent(vararg : Any) : ApplicationEvent(vararg)
