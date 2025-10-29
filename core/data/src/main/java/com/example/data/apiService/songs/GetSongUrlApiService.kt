package com.example.data.apiService.songs

import com.example.data.model.song.SongUrlData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSongUrlApiService {
    @GET("song/url/v1")
    suspend fun getSongUrl(@Query("id") id: String,@Query("level") level: String="exhigh"): Response<SongUrlData>
}