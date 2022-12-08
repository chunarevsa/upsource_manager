package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.developer.Developer
import com.rtkit.upsource_manager.entities.developer.Role
import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.payload.api.FullUserInfoDTO
import com.rtkit.upsource_manager.repositories.DeveloperRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors

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

    fun findNewDeveloperFromParticipants(participants: MutableSet<ParticipantEntity>) {

        val part: MutableSet<ParticipantEntity> = mutableSetOf()
//        participants.forEach { participant ->
//            run {
//                val temp = protocolService.makeRequest(FullUserInfoDTO(participant.userId)).getFirstParticipant()
//                participant.name = temp.name
//                part.add(participant)
//
//            }
//        }
        participants.stream().map { participant ->
            {
                val temp = protocolService.makeRequest(FullUserInfoDTO(participant.userId)).getFirstParticipant()
                participant.name = temp.name
                part.add(participant)
            }
        }

        val names: MutableSet<String> = mutableSetOf()
        part.forEach { part1 -> names.add(part1.name) }
        names

        logger.info("=============")

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


