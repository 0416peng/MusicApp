package com.example.data.di.playList

import com.example.data.apiService.playList.PlayListApiService
import com.example.data.apiService.playList.PlayListDetailApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PlayListModule {
    @Provides
    @Singleton
    fun providePlayListApiService(retrofit: Retrofit): PlayListApiService {
        return retrofit.create(PlayListApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePlayListDetailApiService(retrofit: Retrofit): PlayListDetailApiService {
        return retrofit.create(PlayListDetailApiService::class.java)
    }
}