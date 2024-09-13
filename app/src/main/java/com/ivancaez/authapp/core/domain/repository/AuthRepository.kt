package com.ivancaez.authapp.core.domain.repository

import com.ivancaez.authapp.core.data.remote.dto.auth.AuthRequest
import com.ivancaez.authapp.core.data.remote.dto.auth.AuthResponse
import com.ivancaez.authapp.core.data.remote.dto.auth.RefreshTokenRequest
import com.ivancaez.authapp.core.data.remote.dto.auth.TokenResponse
import com.ivancaez.authapp.core.util.Resource

interface AuthRepository {
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Resource<TokenResponse>
    suspend fun authenticate(authRequest: AuthRequest): Resource<AuthResponse>
}