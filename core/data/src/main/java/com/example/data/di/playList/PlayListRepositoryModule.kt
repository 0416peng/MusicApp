package com.example.data.di.playList

import com.example.data.repository.playList.PlayListRepository
import com.example.data.repository.playList.PlayListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PlayListRepositoryModule {
    @Binds
    abstract fun bindPlayListRepository(impl: PlayListRepositoryImpl): PlayListRepository
}