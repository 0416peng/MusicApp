package com.example.data.apiService.search

import com.example.data.model.search.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchDetailApiService {
    @GET("search")
    suspend fun getSearchDetail(
        @Query("keywords") keywords: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 30,
        @Query("type") type: Int
    ): Response<SearchResult>
}


