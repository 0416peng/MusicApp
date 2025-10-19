package com.example.data.apiService.albumList

import com.example.data.model.albumList.AlbumListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumListApiService {
    @GET("album")
    suspend fun getAlbumListData(@Query("id")id: Long): Response<AlbumListData>
}