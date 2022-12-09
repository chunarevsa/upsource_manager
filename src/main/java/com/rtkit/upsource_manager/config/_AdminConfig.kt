package com.rtkit.upsource_manager.config

import org.springframework.context.annotation.Configuration

/**
 * Шаблон для создания класса AdminConfig
 *
 */
@Configuration
open class _AdminConfig {
    companion object {
        /**
         * В этой переменной хранится логин и пароль админа, от которого будут выполняться большинство запросов
         * Кодировка: Base64
         * Формат: login:password
         */
        private const val encodedData: String = ""
        const val ADMIN_BASIC_AUTH = "Basic $encodedData"
    }
}