package com.example.data.apiService.songs

import com.example.data.model.song.SongDetailData
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface GetSongDetailApiService {
    @GET("song/detail")
    suspend fun getSongDetail(@Query("ids") id: Long): Response<SongDetailData>
}