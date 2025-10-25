package com.example.data.repository.artist

import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs

interface ArtistRepository {
    suspend fun getArtistDetail(id: Long): Result<ArtistDetail>
    suspend fun getArtistHotSongs(id: Long): Result<ArtistHotSongs>
    suspend fun getArtistSongs(id: Long,offset: Int): Result<ArtistSongs>
}


