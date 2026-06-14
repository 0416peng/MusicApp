package com.example.data.apiService.songs

import com.example.data.model.song.LyricData
import com.example.data.model.song.SongDetailData
import com.example.data.model.song.SongUrlData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongApiService {
    @GET("song/url/v1")
    suspend fun getSongUrl(
        @Query("id") id: String,
        @Query("level") level: String = "exhigh"
    ): Response<SongUrlData>

    @GET("song/detail")
    suspend fun getSongDetail(@Query("ids") id: Long): Response<SongDetailData>

    @GET("lyric")
    suspend fun getSongLyric(@Query("id") id: Long): Response<LyricData>
}
