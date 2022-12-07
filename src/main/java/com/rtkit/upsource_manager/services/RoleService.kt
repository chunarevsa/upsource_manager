package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.developer.Role
import com.rtkit.upsource_manager.repositories.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun findAll(): MutableSet<Role> {
        return roleRepository.findAll().toMutableSet()
    }
}