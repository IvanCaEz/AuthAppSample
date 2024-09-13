package com.ivancaez.authapp.core.data.remote.dto.user

data class UserDto(
    val id: Long?,
    val username: String,
    val email: String,
    val image: String,
    val password: String,
    val role: Role
)