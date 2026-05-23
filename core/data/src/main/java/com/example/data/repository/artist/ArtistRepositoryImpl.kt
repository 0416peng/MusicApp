package com.example.data.repository.artist

import com.example.data.apiService.artist.ArtistApiService
import com.example.data.di.apiCall
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistApiService: ArtistApiService
) : ArtistRepository {
    override suspend fun getArtistDetail(id: Long): Result<ArtistDetail> {
        return apiCall {
            artistApiService.getArtistDetail(id).body()!!
        }
    }

    override suspend fun getArtistHotSongs(id: Long): Result<ArtistHotSongs> {
        return apiCall {
            artistApiService.getArtistHotSongs(id).body()!!
        }
    }

    override suspend fun getArtistSongs(id: Long, offset: Int): Result<ArtistSongs> {
        return apiCall {
            artistApiService.getArtistSongs(id, offset).body()!!
        }
    }
}
