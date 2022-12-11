package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.developer.Developer
import com.rtkit.upsource_manager.entities.developer.Role
import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.payload.api.request.UserInfoRequestDTO
import com.rtkit.upsource_manager.payload.api.response.userInfo.Result
import com.rtkit.upsource_manager.repositories.DeveloperRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class DeveloperService(
    private val passwordEncoder: PasswordEncoder,
    private val developerRepository: DeveloperRepository,
    private val roleService: RoleService,
    private val protocolService: ProtocolService
) {
    private val logger: Logger = LogManager.getLogger(DeveloperService::class.java)

    fun addNewDeveloper(login: String, password: String = ""): Developer {
        val newDeveloper = Developer()
        val isAdmin = true // TODO: Пока все админы
        if (password.isNotEmpty()) {
            newDeveloper.password = passwordEncoder.encode(password)
        }
        newDeveloper.login = login
        newDeveloper.addRoles(getRoles(isAdmin))
        newDeveloper.isActive = true
        return saveDeveloper(newDeveloper)
    }

    fun findNewDeveloperFromParticipants(participants: MutableSet<ParticipantEntity>): MutableSet<ParticipantEntity> {
        logger.info("============= Количество участников:${participants.size} =============")

        val listUserIds = mutableListOf<String>()
        participants.forEach { participant ->
            if (!listUserIds.contains(participant.userId)) listUserIds.add(participant.userId)
        }

        // Получаю список всех userId из сущности developer
        val allDev = developerRepository.findAll()
        val listUserIdsFromDb = mutableListOf<String>()
        allDev.forEach { dev -> dev.participants.forEach { participant -> listUserIdsFromDb.add(participant.userId) } }

        listUserIds.removeAll(listUserIdsFromDb)
        listUserIds.forEach { userId -> logger.info("=== Найден новый dev: $userId") }

        val userInfos = mutableListOf<Result?>()
        listUserIds.forEach { userId -> userInfos.add(protocolService.makeRequest(UserInfoRequestDTO(userId))?.result) }

        // TODO: Сохранение DEV

        participants.removeIf { participant -> (participant.name == "guest") }
        return participants
    }

    private fun saveDeveloper(developer: Developer): Developer {
        return developerRepository.save(developer)
    }

    fun developerAlreadyExists(login: String): Boolean {
        return developerRepository.existsByLogin(login)
    }

    fun findByLogin(login: String): Developer {
        val developer = developerRepository.findByLogin(login)
        return if (developer.isPresent) developer.get() else throw Exception("Participant is not exists")
    }

    private fun getRoles(isAdmin: Boolean): MutableSet<Role> {
        val roles = roleService.findAll()
        if (!isAdmin) {
            roles.removeIf(Role::isAdminRole)
        }
        return roles
    }

}


