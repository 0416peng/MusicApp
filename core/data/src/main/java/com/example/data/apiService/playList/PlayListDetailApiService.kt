package com.example.data.apiService.playList

import com.example.data.model.playList.PlayListData
import com.example.data.model.playList.PlayListDetailData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayListDetailApiService {
    @GET("/playlist/detail")
    suspend fun getPlayListDetailData(@Query("id") id: Long): Response<PlayListDetailData>
}