package com.example.data.di.song

import com.example.data.repository.song.SongRepository
import com.example.data.repository.song.SongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class SongRepositoryModule {
    @Binds
    abstract fun bindSongRepository(impl: SongRepositoryImpl): SongRepository
}