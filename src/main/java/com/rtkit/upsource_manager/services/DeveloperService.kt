package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.developer.Developer
import com.rtkit.upsource_manager.entities.developer.Role
import com.rtkit.upsource_manager.entities.participant.ParticipantEntity
import com.rtkit.upsource_manager.payload.api.FullUserInfoDTO
import com.rtkit.upsource_manager.payload.pacer.review.Participant
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

        // только уникальные <participant.userId, Participant>
        // TODO: посмотреть что ещё прилетает в запросе
        //        {"result":
//            {"infos":[
//                {"userId":"7da228a8-6d76-41cd-b88f-cfc1f6f67710",
//                    "name":"Лихачев Александр Анатольевич",
//                    "isResolved":true,
//                    "isMe":false,
//                    "avatarUrl":"https://codereview.ritperm.rt.ru/hub/api/rest/avatar/7da228a8-6d76-41cd-b88f-cfc1f6f67710",
//                    "profileUrl":"https://codereview.ritperm.rt.ru/hub/users/7da228a8-6d76-41cd-b88f-cfc1f6f67710",
//                    "login":"likhachev-aa"}]}}
        val temp: MutableMap<String, ParticipantEntity> = mutableMapOf()

        try {
            participants.forEach { participant ->
                run {
                    if (temp.containsKey(participant.userId)) {
                        participant.name = temp[participant.userId]!!.name
                    } else {
                        participant.name =
                            protocolService.makeRequest(FullUserInfoDTO(participant.userId)).getFirstParticipant().name
                        temp[participant.userId] = participant
                    }
                }
            }
        } catch (e: Exception) {
            logger.info(e)
        }

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


