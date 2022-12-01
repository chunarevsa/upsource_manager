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
    fun addNewUser(login: String, password: String): User {
        val newUser = User(login, passwordEncoder.encode(password), true)
        return saveUser(newUser)
    }

    private fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun userAlreadyExists(login: String): Boolean {
        return userRepository.existsByLogin(login)
    }

    fun findByLogin(login: String): User {
        val user = userRepository.findByLogin(login)
        return if (user.isPresent) user.get() else throw Exception("User is not exists")
    }

}