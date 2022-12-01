package com.rtkit.upsource_manager.payload.auth

data class JwtAuthenticationResponse(
    val jwtToken: String,
    val expiryDuration: Long,
    val tokenType: String = "Bearer "
)