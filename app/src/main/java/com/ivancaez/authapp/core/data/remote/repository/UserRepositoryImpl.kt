package com.ivancaez.authapp.core.data.remote.repository

import com.ivancaez.authapp.core.data.remote.api.UserApi
import com.ivancaez.authapp.core.data.remote.dto.user.UserDto
import com.ivancaez.authapp.core.domain.repository.UserRepository
import com.ivancaez.authapp.core.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {
    override suspend fun register(userDto: UserDto): Resource<UserDto> {
        val response = try {
            userApi.register(userDto)
        } catch (e: IOException) {
            e.printStackTrace()
            return Resource.Error(e.message!!)
        } catch (e: HttpException) {
            e.printStackTrace()
            return Resource.Error(e.message!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message!!)
        }
        return if (response.isSuccessful){
            Resource.Success(response.body()!!)
        } else {
            Resource.Error(response.message())
        }
    }

    override suspend fun getUserByEmail(email: String, token: String): Resource<UserDto> {
        val response = try {
            val jwtToken = "Bearer $token"
            userApi.getUserByEmail(jwtToken, email)
        } catch (e: IOException) {
            e.printStackTrace()
            return Resource.Error(e.message!!)
        } catch (e: HttpException) {
            e.printStackTrace()
            return Resource.Error(e.message!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message!!)
        }
        return if (response.isSuccessful){
            Resource.Success(response.body()!!)
        } else {
            Resource.Error(response.message())
        }
    }
}