package com.example.data.di.search

import com.example.data.apiService.search.HotSearchApiService
import com.example.data.apiService.search.SearchDetailApiService
import com.example.data.apiService.search.SearchSuggestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): HotSearchApiService {
        return retrofit.create(HotSearchApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideSearchSuggestApiService(retrofit: Retrofit): SearchSuggestApiService {
        return retrofit.create(SearchSuggestApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideSearchDetailApiService(retrofit: Retrofit): SearchDetailApiService {
        return retrofit.create(SearchDetailApiService::class.java)
    }
}