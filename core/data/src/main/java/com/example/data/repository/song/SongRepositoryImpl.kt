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
            songApiService.getSongUrl(idsString).body()!!
        }
    }

    override suspend fun getSongDetail(id: Long): Result<SongDetailData> {
        return apiCall {
            songApiService.getSongDetail(id).body()!!
        }
    }

    override suspend fun getSongLyric(id: Long): Result<LyricData> {
        return apiCall {
            songApiService.getSongLyric(id).body()!!
        }
    }

}
