package com.example.data.di.auth

import com.example.data.apiService.auth.AuthApiService
import com.example.data.apiService.auth.LoginApiService
import com.example.data.apiService.auth.LoginPicApiService
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

object AuthModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideLoginPicApiService(retrofit: Retrofit): LoginPicApiService {
        return retrofit.create(LoginPicApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideAuthPicApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}
