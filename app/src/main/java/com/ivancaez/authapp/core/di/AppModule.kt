package com.ivancaez.authapp.core.di

import com.google.gson.GsonBuilder
import com.ivancaez.authapp.core.data.remote.api.AuthApi
import com.ivancaez.authapp.core.data.remote.api.UserApi
import com.ivancaez.authapp.core.data.remote.repository.AuthRepositoryImpl
import com.ivancaez.authapp.core.data.remote.repository.UserRepositoryImpl
import com.ivancaez.authapp.core.domain.repository.AuthRepository
import com.ivancaez.authapp.core.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Auth

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val gson = GsonBuilder()
            .serializeNulls()
            .create()
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    // User

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val gson = GsonBuilder()
            .serializeNulls()
            .create()
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(UserApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }


    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepositoryImpl(userApi)
    }
}