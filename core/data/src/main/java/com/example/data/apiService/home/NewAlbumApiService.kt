package com.example.data.apiService.home

import com.example.data.model.home.NewAlbumData
import retrofit2.Response
import retrofit2.http.GET

interface NewAlbumApiService {
    @GET("album/newest")
    suspend fun getNewAlbum(): Response<NewAlbumData>
}