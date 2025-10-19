package com.example.data.di.AlbumList

import com.example.data.apiService.albumList.AlbumListApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AlbumListModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AlbumListApiService {
        return retrofit.create(AlbumListApiService::class.java)
}}