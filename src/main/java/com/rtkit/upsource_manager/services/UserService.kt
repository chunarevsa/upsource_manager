package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.payload.upsource.user.FindUsersRequestDTO
import com.rtkit.upsource_manager.payload.upsource.user.Info
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class UserService(
    private val protocolService: ProtocolService
) {
    private val logger: Logger = LogManager.getLogger(UserService::class.java)
    private val userList = mutableSetOf<Info>()

    fun updateUsers() {
        val userFromRequest = protocolService.makeRequest(FindUsersRequestDTO(limit = 500)).infos

        userFromRequest.forEach { user ->
            if (!userList.contains(user)) {
                userList.add(user)
                Config.addUpsourceUserLogin(user.login)
                logger.info("========== Добавлен новый пользователь ${user.name}")
            }
        }

        logger.info("=== Количество разработчиков: ${userList.size}")
    }

}


