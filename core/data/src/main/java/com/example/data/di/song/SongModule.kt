package com.example.data.di.song

import com.example.data.apiService.songs.SongApiService
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
    fun provideSongApiService(retrofit: Retrofit): SongApiService {
        return retrofit.create(SongApiService::class.java)
    }
}
