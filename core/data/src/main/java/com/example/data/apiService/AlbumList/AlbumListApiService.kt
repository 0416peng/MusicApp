package com.example.data.apiService.AlbumList

import com.example.data.model.AlbumListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumListApiService {
    @GET("album")
    suspend fun getAlbumListData(@Query("id")id:Int): Response<AlbumListData>
}