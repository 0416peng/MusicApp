package com.example.data.apiService.playList


import com.example.data.model.PlayListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayListApiService {
    @GET("/playlist/track/all")
    suspend fun getPlayListData(@Query("id") id: Long): Response<PlayListData>
}