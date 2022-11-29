package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.auth.LoginRequest
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val connectionService: ConnectionService
) {
    fun authenticateUser(loginRequest: LoginRequest) {
        val basicAuth: String = loginRequest.login + ":" + loginRequest.password
        connectionService.makeTrialConnection(basicAuth)
    }

}