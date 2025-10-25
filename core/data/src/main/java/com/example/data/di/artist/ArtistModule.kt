package com.example.data.di.artist

import com.example.data.apiService.artist.ArtistDetailApiService
import com.example.data.apiService.artist.ArtistHotSongsApiService
import com.example.data.apiService.artist.ArtistSongsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ArtistModule {
    @Provides
    @Singleton
    fun provideArtistDetailApiService(retrofit: Retrofit): ArtistDetailApiService {
        return retrofit.create(ArtistDetailApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideArtistHotSongsApiService(retrofit: Retrofit): ArtistHotSongsApiService {
        return retrofit.create(ArtistHotSongsApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideArtistSongsApiService(retrofit: Retrofit): ArtistSongsApiService {
        return retrofit.create(ArtistSongsApiService::class.java)
    }
}