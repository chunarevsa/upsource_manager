package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.developer.Developer
import com.rtkit.upsource_manager.entities.developer.DeveloperStatus
import com.rtkit.upsource_manager.entities.developer.Role
import com.rtkit.upsource_manager.payload.api.user.FindUsersRequestDTO
import com.rtkit.upsource_manager.payload.api.user.Info
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

    fun validateDeveloper(login: String, password: String) {
        if (!developerAlreadyExists(login)) {
            addNewDeveloper(login, password)
        }

        val dev = findByLogin(login)
        if (dev.password.isNullOrEmpty() && dev.status == DeveloperStatus.NOT_VERIFIED) {
            dev.status = DeveloperStatus.VERIFIED
            logger.info("========== Новый верифицированный пользователь: ${dev.name} ==========")
        }

        dev.password = passwordEncoder.encode(password)
        saveDeveloper(dev)
    }

    fun updateDevelopers() {
        val devFromDB = findAll()
        val devFromRequest = protocolService.makeRequest(FindUsersRequestDTO(limit = 500))?.infos
            ?: throw Exception()

        val listUserIdsFromDb = mutableListOf<String>()
        val listUserIdsRequest = mutableListOf<String>()

        devFromDB.forEach { dev -> listUserIdsFromDb.add(dev.userId) }
        devFromRequest.forEach { dev -> listUserIdsRequest.add(dev.userId) }

        // Если такой ид уже есть в БД - удаляем
        listUserIdsRequest.removeAll(listUserIdsFromDb)
        // Оставляем инфу только о новых разработчиках
        devFromRequest.removeIf { dev -> (!listUserIdsRequest.contains(dev.userId)) }
        devFromRequest.removeIf { dev -> (dev.name == "guest") }

        devFromRequest.forEach { dev -> addDevFromRequest(dev) }
    }

    private fun addDevFromRequest(dev: Info) {
        val newDev = Developer().apply {
            login = dev.login
            password = ""
            userId = dev.userId
            name = dev.name
            avatarUrl = dev.avatarUrl
            email = dev.email
            profileUrl = dev.profileUrl
            isActive = true
            status = DeveloperStatus.NOT_VERIFIED
        }
        saveDeveloper(newDev)
        logger.info("========== Добавлен новый разработчик: ${newDev.name} ==========")
    }


    private fun addNewDeveloper(login: String, password: String): Developer {
        val newDeveloper = Developer()
        val isAdmin = true // TODO: Пока все админы
        newDeveloper.password = passwordEncoder.encode(password)
        newDeveloper.login = login
        newDeveloper.addRoles(getRoles(isAdmin))
        newDeveloper.isActive = true
        newDeveloper.status = DeveloperStatus.NO_INFO
        return saveDeveloper(newDeveloper)
    }

    private fun getRoles(isAdmin: Boolean): MutableSet<Role> {
        val roles = roleService.findAll()
        if (!isAdmin) {
            roles.removeIf(Role::isAdminRole)
        }
        return roles
    }

    private fun saveDeveloper(developer: Developer): Developer {
        return developerRepository.save(developer)
    }

    private fun developerAlreadyExists(login: String): Boolean {
        return developerRepository.existsByLogin(login)
    }

    private fun findByLogin(login: String): Developer {
        val developer = developerRepository.findByLogin(login)
        return if (developer.isPresent) developer.get() else throw Exception("Participant is not exists")
    }

    private fun findAll(): MutableSet<Developer> {
        return developerRepository.findAll().toMutableSet()
    }

}


