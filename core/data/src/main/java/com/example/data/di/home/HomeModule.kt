package com.example.data.di.home

import com.example.data.apiService.auth.LoginApiService
import com.example.data.apiService.home.RecommendAlbumApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RecommendAlbumApiService {
        return retrofit.create(RecommendAlbumApiService::class.java)
    }
}