package com.example.data.apiService.artist

import com.example.data.model.artist.ArtistSongs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistSongsApiService {
    @GET("artist/songs")
    suspend fun getArtistSongs(@Query("id") id: Long,@Query("offset") offset: Int,@Query("limit") limit: Int=50): Response<ArtistSongs>
}
