package com.ivancaez.authapp.core.domain.repository

import com.ivancaez.authapp.core.data.remote.dto.user.UserDto
import com.ivancaez.authapp.core.util.Resource

interface UserRepository {
    suspend fun register(userDto: UserDto): Resource<UserDto>
    suspend fun getUserByEmail(email: String, token: String): Resource<UserDto>
}