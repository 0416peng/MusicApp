package com.example.data.apiService.home

import com.example.data.model.home.BannerData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BannerApiService {
    @GET("banner")
    suspend fun getBanner(@Query("type") type: Int = 1): Response<BannerData>
}