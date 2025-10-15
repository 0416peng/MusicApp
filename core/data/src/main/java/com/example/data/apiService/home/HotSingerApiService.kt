package com.example.data.apiService.home

import com.example.data.model.home.HotSingerData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HotSingerApiService {
    @GET("/top/artists")
    suspend fun getHotSinger(@Query("offset") offset: Int=0, @Query("limit") limit: Int=15): Response<HotSingerData>
}