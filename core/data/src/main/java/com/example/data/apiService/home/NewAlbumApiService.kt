package com.example.data.apiService.home

import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewAlbumApiService {
    @GET("album/newest")
    suspend fun getNewAlbum(): Response<NewAlbumData>
}