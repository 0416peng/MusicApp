package com.example.data.apiService.songs

import com.example.data.model.song.LyricData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSongLyricApiService {
    @GET("lyric")
    suspend fun getSongLyric(@Query("id") id: Long): Response<LyricData>

}