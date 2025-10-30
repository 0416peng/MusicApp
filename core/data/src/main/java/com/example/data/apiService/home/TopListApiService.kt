package com.example.data.apiService.home

import com.example.data.model.home.TopListData
import retrofit2.Response
import retrofit2.http.GET

interface TopListApiService {
    @GET("toplist")
    suspend fun getTopList(): Response<TopListData>
}