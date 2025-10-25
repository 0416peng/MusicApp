package com.example.data.di.artist

import com.example.data.repository.artist.ArtistRepository
import com.example.data.repository.artist.ArtistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistRepositoryModule {
    @Binds
    abstract fun bindArtistRepository(impl: ArtistRepositoryImpl): ArtistRepository

}