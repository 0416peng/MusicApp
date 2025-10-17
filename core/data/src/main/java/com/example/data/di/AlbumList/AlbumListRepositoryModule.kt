package com.example.data.di.AlbumList

import com.example.data.repository.AlbumList.AlbumListRepository
import com.example.data.repository.AlbumList.AlbumListRepositoryImpl
import com.example.data.repository.auth.AuthRepository
import com.example.data.repository.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumListRepositoryModule {
    @Binds
    abstract fun bindAuthRepository(impl: AlbumListRepositoryImpl): AlbumListRepository

}