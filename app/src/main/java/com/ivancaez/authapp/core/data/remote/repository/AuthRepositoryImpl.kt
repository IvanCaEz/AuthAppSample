package com.ivancaez.authapp.core.data.remote.repository

import com.ivancaez.authapp.core.data.remote.api.AuthApi
import com.ivancaez.authapp.core.data.remote.dto.auth.AuthRequest
import com.ivancaez.authapp.core.data.remote.dto.auth.AuthResponse
import com.ivancaez.authapp.core.data.remote.dto.auth.RefreshTokenRequest
import com.ivancaez.authapp.core.data.remote.dto.auth.TokenResponse
import com.ivancaez.authapp.core.domain.repository.AuthRepository
import com.ivancaez.authapp.core.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): AuthRepository {
    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Resource<TokenResponse> {
        val response = try {
            authApi.refreshToken(refreshTokenRequest)
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

    override suspend fun authenticate(authRequest: AuthRequest): Resource<AuthResponse> {
        val response = try {
            authApi.authenticate(authRequest)
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