package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.user.User
import com.rtkit.upsource_manager.payload.auth.LoginRequest
import com.rtkit.upsource_manager.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {
    fun addNewUser(loginRequest: LoginRequest): User {
        val newUser = User(loginRequest.login, passwordEncoder.encode(loginRequest.password))
        return saveUser(newUser)
    }

    private fun saveUser(user: User): User {
        return userRepository.save(user)
    }

}