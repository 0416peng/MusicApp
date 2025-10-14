package com.example.data.apiService.home

import com.example.data.model.home.BannerData
import retrofit2.Response
import retrofit2.http.GET

interface BannerApiService {
    @GET("banner")
    suspend fun getBanner(): Response<BannerData>
}