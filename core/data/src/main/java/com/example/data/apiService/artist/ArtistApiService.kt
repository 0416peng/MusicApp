package com.example.data.apiService.artist

import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistApiService {
    @GET("artist/detail")
    suspend fun getArtistDetail(@Query("id") id: Long): Response<ArtistDetail>

    @GET("artist/top/song")
    suspend fun getArtistHotSongs(@Query("id") id: Long): Response<ArtistHotSongs>

    @GET("artist/songs")
    suspend fun getArtistSongs(
        @Query("id") id: Long,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 50
    ): Response<ArtistSongs>
}
