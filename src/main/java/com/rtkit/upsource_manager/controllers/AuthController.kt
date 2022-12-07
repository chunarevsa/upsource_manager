package com.rtkit.upsource_manager.controllers

import com.rtkit.upsource_manager.payload.auth.LoginRequest
import com.rtkit.upsource_manager.services.AuthService
import com.rtkit.upsource_manager.services.ProjectService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth/")
open class AuthController(
    private val authService: AuthService,
    private val projectService: ProjectService
) {

    private val logger: Logger = LogManager.getLogger(AuthController::class.java)

    /**
     * Авторизация по логину паролю
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    open fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        // TODO: используем пока LoginRequest Когда будет фронт, будем тянуть оттуда данные
        val jwtAuthenticationResponse = authService.authenticateParticipant(loginRequest.login, loginRequest.password)
        return ResponseEntity.ok(jwtAuthenticationResponse)
    }

}