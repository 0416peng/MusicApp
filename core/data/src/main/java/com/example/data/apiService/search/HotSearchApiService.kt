package com.example.data.apiService.search

import com.example.data.model.search.HotSearchData
import retrofit2.Response
import retrofit2.http.GET

interface HotSearchApiService {
    @GET("search/hot")
    suspend fun getHotSearchData(): Response<HotSearchData>
}