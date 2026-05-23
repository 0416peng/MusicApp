package com.example.data.di.artist

import com.example.data.apiService.artist.ArtistApiService
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
    fun provideArtistApiService(retrofit: Retrofit): ArtistApiService {
        return retrofit.create(ArtistApiService::class.java)
    }
}
