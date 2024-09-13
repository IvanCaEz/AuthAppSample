package com.ivancaez.authapp.core.data.remote.api

import com.ivancaez.authapp.core.data.remote.dto.auth.AuthRequest
import com.ivancaez.authapp.core.data.remote.dto.auth.AuthResponse
import com.ivancaez.authapp.core.data.remote.dto.auth.RefreshTokenRequest
import com.ivancaez.authapp.core.data.remote.dto.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/v1/auth")
    suspend fun authenticate(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<TokenResponse>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/v1/auth/"
    }
}