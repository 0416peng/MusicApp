package com.example.data.repository.song

import com.example.data.model.song.SongUrlData

interface SongRepository {
    suspend fun getSongUrl(id: Long): Result<SongUrlData>
}