package com.example.data.repository.song

import com.example.data.apiService.songs.SongApiService
import com.example.data.di.apiCall
import com.example.data.model.song.LyricData
import com.example.data.model.song.SongDetailData
import com.example.data.model.song.SongUrlData
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songApiService: SongApiService
) : SongRepository {
    override suspend fun getSongUrl(ids: List<Long>): Result<SongUrlData> {
        return apiCall {
            val idsString = ids.joinToString(separator = ",")
            val response = songApiService.getSongUrl(idsString)
            response.body() ?: throw NullPointerException("Response body is null, code=${response.code()}")
        }
    }

    override suspend fun getSongDetail(id: Long): Result<SongDetailData> {
        return apiCall {
            val response = songApiService.getSongDetail(id)
            response.body() ?: throw NullPointerException("Response body is null, code=${response.code()}")
        }
    }

    override suspend fun getSongLyric(id: Long): Result<LyricData> {
        return apiCall {
            val response = songApiService.getSongLyric(id)
            response.body() ?: throw NullPointerException("Response body is null, code=${response.code()}")
        }
    }

}
