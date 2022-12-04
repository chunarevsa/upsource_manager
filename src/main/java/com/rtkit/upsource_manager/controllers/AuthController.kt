package com.rtkit.upsource_manager.controllers

import com.rtkit.upsource_manager.payload.auth.LoginRequest
import com.rtkit.upsource_manager.services.AuthService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth/")
open class AuthController(private val authService: AuthService) {

    private val logger: Logger = LogManager.getLogger(AuthController::class.java)

    /**
     * Авторизация по логину паролю
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    open fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        // TODO: используем пока LoginRequest Когда будет фронт, будем тянуть оттуда данные
        val jwtAuthenticationResponse = authService.authenticateParticipant(loginRequest)
        logger.info(jwtAuthenticationResponse.toString()) // TODO: Убрать
        return ResponseEntity.ok(jwtAuthenticationResponse)
    }
}