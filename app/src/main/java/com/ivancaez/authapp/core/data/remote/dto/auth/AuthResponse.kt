package com.ivancaez.authapp.core.data.remote.dto.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
