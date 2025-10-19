package com.example.data.di.AlbumList

import com.example.data.repository.albumList.AlbumListRepository
import com.example.data.repository.albumList.AlbumListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumListRepositoryModule {
    @Binds
    abstract fun bindAlbumRepository(impl: AlbumListRepositoryImpl): AlbumListRepository

}