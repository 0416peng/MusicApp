package com.example.data.apiService.search

import com.example.data.model.search.HotSearchData
import com.example.data.model.search.SearchResult
import com.example.data.model.search.SearchSuggestData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/hot")
    suspend fun getHotSearchData(): Response<HotSearchData>

    @GET("/search/suggest")
    suspend fun getSearchSuggest(
        @Query("keywords") keywords: String,
        @Query("type") type: String = "mobile"
    ): Response<SearchSuggestData>

    @GET("search")
    suspend fun getSearchDetail(
        @Query("keywords") keywords: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 30,
        @Query("type") type: Int
    ): Response<SearchResult>
}
