package com.example.data.di.search

import com.example.data.repository.playList.PlayListRepository
import com.example.data.repository.playList.PlayListRepositoryImpl
import com.example.data.repository.search.SearchDetailRepository
import com.example.data.repository.search.SearchRepository
import com.example.data.repository.search.SearchRepositoryDetailImpl
import com.example.data.repository.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {
    @Binds
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
    @Binds
    abstract fun bindSearchDetailRepository(impl: SearchRepositoryDetailImpl): SearchDetailRepository
}