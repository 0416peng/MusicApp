package com.example.data.repository.song

import com.example.data.apiService.songs.GetSongUrlApiService
import com.example.data.model.song.SongUrlData
import java.io.IOException
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val getSongUrlApiService: GetSongUrlApiService
) : SongRepository{
    override suspend fun getSongUrl(ids: List<Long>): Result<SongUrlData> {
        return try {
            val idsString=ids.joinToString(separator = ",")
            val response =getSongUrlApiService.getSongUrl(idsString)
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