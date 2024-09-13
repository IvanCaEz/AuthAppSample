package com.ivancaez.authapp.core.data.remote.api

import com.ivancaez.authapp.core.data.remote.dto.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/v1/users")
    suspend fun register(@Body userDto: UserDto): Response<UserDto>

    @GET("email/{email}")
    suspend fun getUserByEmail(
        @Header("Authorization") token: String,
        @Path("email") email: String): Response<UserDto>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/v1/users/"
    }
}