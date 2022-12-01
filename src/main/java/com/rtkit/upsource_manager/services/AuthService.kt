package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.auth.JwtAuthenticationResponse
import com.rtkit.upsource_manager.payload.auth.LoginRequest
import com.rtkit.upsource_manager.security.jwt.JwtTokenProvider
import com.rtkit.upsource_manager.security.jwt.JwtUser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val connectionService: ConnectionService,
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    private val logger: Logger = LogManager.getLogger(AuthService::class.java)

    fun authenticateUser(loginRequest: LoginRequest): JwtAuthenticationResponse {
        val login = loginRequest.login
        val password = loginRequest.password

        validateAuthenticatedData("$login:$password")
        if (!userService.userAlreadyExists(login)) userService.addNewUser(login, password)

        val user = UsernamePasswordAuthenticationToken(login, password)
        val authentication: Authentication = authenticationManager.authenticate(user)
        val jwtUser: JwtUser = authentication.principal as JwtUser
        SecurityContextHolder.getContext().authentication = authentication

        //TODO: Этот токен нужно как-то подставить в хедер Authen... На беке или на фронте?
        // пока возвращаем его
        return JwtAuthenticationResponse(
            jwtToken = createJwtToken(jwtUser),
            expiryDuration = jwtTokenProvider.expiryDuration
        )
    }

    private fun createJwtToken(jwtUser: JwtUser): String {
        val token = jwtTokenProvider.createToken(jwtUser)
        logger.info(token) // TODO: Убрать
        return token
    }

    private fun validateAuthenticatedData(basicAuth: String) {
        connectionService.makeTrialConnection(basicAuth) ?: throw Exception("Неверные логин и пароль")
        logger.info("Authenticated data is validated")
    }

}