package com.example.data.repository.song

import com.example.data.model.song.SongDetailData
import com.example.data.model.song.SongUrlData

interface SongRepository {
    suspend fun getSongUrl(ids: List<Long>): Result<SongUrlData>
    suspend fun getSongDetail(id: Long): Result<SongDetailData>
}