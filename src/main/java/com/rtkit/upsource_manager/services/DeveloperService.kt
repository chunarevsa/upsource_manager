package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.developer.Developer
import com.rtkit.upsource_manager.entities.developer.Role
import com.rtkit.upsource_manager.repositories.DeveloperRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class DeveloperService(
    private val passwordEncoder: PasswordEncoder,
    private val developerRepository: DeveloperRepository,
    private val roleService: RoleService
) {
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


