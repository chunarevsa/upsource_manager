package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.auth.LoginRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val connectionService: ConnectionService,
    private val userService: UserService
) {
    fun authenticateUser(loginRequest: LoginRequest) {
        val basicAuth: String = loginRequest.login + ":" + loginRequest.password
        validateAuthenticatedData(basicAuth)
        userService.addNewUser(loginRequest)
        val user = UsernamePasswordAuthenticationToken(loginRequest.login, loginRequest.password)

    }

    private fun validateAuthenticatedData(basicAuth: String) {
        connectionService.makeTrialConnection(basicAuth) ?: throw Exception("Неверные логин и пароль")
    }

}