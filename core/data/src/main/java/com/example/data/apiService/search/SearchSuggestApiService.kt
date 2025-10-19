package com.example.data.apiService.search

import com.example.data.model.search.SearchSuggestData
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchSuggestApiService {
    @GET("/search/suggest")
    suspend fun getSearchSuggest(@Query("keywords") keywords: String,@Query("type") type: String="mobile"): Response<SearchSuggestData>

}