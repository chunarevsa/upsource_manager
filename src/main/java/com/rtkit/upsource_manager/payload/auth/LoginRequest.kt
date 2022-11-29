package com.rtkit.upsource_manager.payload.auth

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class LoginRequest(
    @NotBlank(message = "Login cannot be blank")
    @NotNull(message = "Login cannot be null")
    val login: String,

    @NotBlank(message = "Password cannot be null")
    @NotNull(message = "Password cannot be blank")
    val password: String
)


