package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.api.request.ProjectIdDTO
import com.rtkit.upsource_manager.payload.auth.JwtAuthenticationResponse
import com.rtkit.upsource_manager.security.jwt.JwtTokenProvider
import com.rtkit.upsource_manager.security.jwt.JwtUser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val developerService: DeveloperService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val protocolService: ProtocolService
) {
    private val logger: Logger = LogManager.getLogger(AuthService::class.java)

    fun authenticateParticipant(login: String, password: String): JwtAuthenticationResponse {
        val authData = getBasicAuthData(login, password)
        validateAuthenticatedData(authData)
        developerService.validateDeveloper(login, password)

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

    private fun getBasicAuthData(login: String, password: String): String {
        val data = String(Base64.getEncoder().encode("$login:$password".toByteArray()))
        return "Basic $data"
    }

    private fun createJwtToken(jwtUser: JwtUser): String {
        return jwtTokenProvider.createToken(jwtUser)
    }

    /** Нужно делать при каждом логине, чтобы пароль был актуальный */
    private fun validateAuthenticatedData(authData: String) {
        protocolService.makeRequest(ProjectIdDTO().apply { setAuth(authData) })
            ?: throw Exception("Authenticated data is not validated")
        logger.info("Authenticated data is validated")
    }
}