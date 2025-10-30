package com.example.data.di.auth

import com.example.data.apiService.auth.AuthApiService
import com.example.data.apiService.auth.LoginApiService
import com.example.data.apiService.auth.LoginPicApiService
import com.example.data.apiService.auth.VisitorLoginApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    @Singleton
    fun provideVisitorLoginApiService(retrofit: Retrofit): VisitorLoginApiService {
        return retrofit.create(VisitorLoginApiService::class.java)
    }
}
