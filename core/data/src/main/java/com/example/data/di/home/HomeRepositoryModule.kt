package com.example.data.di.home

import com.example.data.repository.home.HomeRepository
import com.example.data.repository.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
}
