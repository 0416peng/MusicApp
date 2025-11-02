package com.example.data.di.song

import com.example.data.apiService.songs.GetSongDetailApiService
import com.example.data.apiService.songs.GetSongUrlApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SongModule {
    @Provides
    @Singleton
    fun provideGetSongUrlApiService(retrofit: Retrofit): GetSongUrlApiService {
        return retrofit.create(GetSongUrlApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideGetSongDetailApiService(retrofit: Retrofit): GetSongDetailApiService {
        return retrofit.create(GetSongDetailApiService::class.java)
    }
}