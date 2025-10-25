package com.example.data.apiService.artist

import com.example.data.model.artist.ArtistHotSongs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistHotSongsApiService {
    @GET("artist/top/song")
    suspend fun getArtistHotSongs(@Query("id") id: Long): Response<ArtistHotSongs>

}