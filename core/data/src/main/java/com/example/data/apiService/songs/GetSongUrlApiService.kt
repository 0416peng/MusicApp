package com.example.data.apiService.songs

import com.example.data.model.song.SongUrlData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSongUrlApiService {
    @GET("")
    suspend fun getSongUrl(@Query("id") id: Long,@Query("level") level: String="exhigh"): Response<SongUrlData>
}