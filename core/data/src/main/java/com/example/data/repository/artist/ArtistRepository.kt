package com.example.data.repository.artist

import com.example.common.ApiResult
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    fun getArtistDetail(id: Long): Flow<ApiResult<ArtistDetail>>
    fun getArtistHotSongs(id: Long):Flow<ApiResult<ArtistHotSongs>>
    fun getArtistSongs(id: Long,offset: Int): Flow<ApiResult<ArtistSongs>>
}


