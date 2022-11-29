package com.rtkit.upsource_manager.controllers

import com.rtkit.upsource_manager.payload.auth.LoginRequest
import com.rtkit.upsource_manager.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
open class AuthController(private val authService: AuthService) {

    /**
     * Авторизация по логину паролю
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    open fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {

        authService.authenticateUser(loginRequest)

//        val authentication: Authentication = authService.authenticateUser(loginRequest)
//        orElseThrow { UserLoginException("аутентификации", loginRequest.getEmail()) }
//        val jwtUser = authentication.principal as JwtUser
//        TempAuthController.logger.info("Вход в систему  " + jwtUser.username)
//        SecurityContextHolder.getContext().authentication = authentication
//        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
//            .map<Any>(RefreshToken::getToken)
//            .map { refreshToken: Any? ->
//                val jwtToken = authService.createToken(jwtUser)
//                ResponseEntity.ok<Any>(
//                    JwtAuthenticationResponse(
//                        jwtToken,
//                        refreshToken,
//                        jwtTokenProvider.getExpiryDuration()
//                    )
//                )
//            }.orElseThrow(Supplier<RuntimeException> {
//                UserLoginException(
//                    "создания токена",
//                    loginRequest.getEmail()
//                )
//            })
        return ResponseEntity(HttpStatus.ACCEPTED)
    }
}