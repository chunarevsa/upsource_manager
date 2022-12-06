package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.prticipant.Participant
import com.rtkit.upsource_manager.entities.prticipant.Role
import com.rtkit.upsource_manager.repositories.ParticipantRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val passwordEncoder: PasswordEncoder,
    private val participantRepository: ParticipantRepository,
    private val roleService: RoleService
) {
    fun addNewParticipant(login: String, password: String): Participant {
        val newParticipant = Participant()
        val isAdmin = true // TODO: Пока все админы
        newParticipant.password = passwordEncoder.encode(password)
        newParticipant.login = login
        newParticipant.addRoles(getRoles(isAdmin))
        newParticipant.isActive = true
        return saveParticipant(newParticipant)
    }

    private fun saveParticipant(participant: Participant): Participant {
        return participantRepository.save(participant)
    }

    fun participantAlreadyExists(login: String): Boolean {
        return participantRepository.existsByLogin(login)
    }

    fun findByLogin(login: String): Participant {
        val participant = participantRepository.findByLogin(login)
        return if (participant.isPresent) participant.get() else throw Exception("Participant is not exists")
    }

    private fun getRoles(isAdmin: Boolean): MutableSet<Role> {
        val roles = roleService.findAll()
        if (!isAdmin) {
            roles.removeIf(Role::isAdminRole)
        }
        return roles
    }

    fun getAdmin(): Participant {
        // TODO: Можно сделать поиск любого пользователя с ролью админ
        // пока используем первого
        return participantRepository.findById(1).get()
    }

}


