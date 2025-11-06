package com.example.data.repository.song

import com.example.data.apiService.songs.GetSongDetailApiService
import com.example.data.apiService.songs.GetSongLyricApiService
import com.example.data.apiService.songs.GetSongUrlApiService
import com.example.data.model.song.LyricData
import com.example.data.model.song.SongDetailData
import com.example.data.model.song.SongUrlData
import java.io.IOException
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val getSongUrlApiService: GetSongUrlApiService,
    private val getSongDetailApiService: GetSongDetailApiService,
    private val getSongLyricApiService: GetSongLyricApiService
) : SongRepository {
    override suspend fun getSongUrl(ids: List<Long>): Result<SongUrlData> {
        return try {
            val idsString = ids.joinToString(separator = ",")
            val response = getSongUrlApiService.getSongUrl(idsString)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null"))
                }
            } else {
                Result.failure(IOException("API Error : ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSongDetail(id: Long): Result<SongDetailData> {
        return try {
            val response = getSongDetailApiService.getSongDetail(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null"))
                }
            } else {
                Result.failure(IOException("API Error : ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSongLyric(id: Long): Result<LyricData> {
        return try {
            val response = getSongLyricApiService.getSongLyric(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null"))
                }
            } else {
                Result.failure(IOException("API Error : ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}