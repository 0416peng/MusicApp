package com.example.data.apiService.artist

import com.example.data.model.artist.ArtistDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistDetailApiService {
    @GET("artist/detail")
    suspend fun getArtistDetail(@Query("id") id: Long): Response<ArtistDetail>
}


