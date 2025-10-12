package com.example.data.apiService.home



import com.example.data.model.home.RecommendAlbumData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecommendAlbumApiService {
    @GET("personalized")
    suspend fun getStatue(@Query("limit") limit: Int): Response<RecommendAlbumData>
}