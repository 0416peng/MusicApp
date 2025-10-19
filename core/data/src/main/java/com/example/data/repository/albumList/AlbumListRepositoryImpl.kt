package com.example.data.repository.albumList

import android.util.Log
import com.example.data.apiService.albumList.AlbumListApiService
import com.example.data.model.albumList.AlbumListData
import java.io.IOException
import javax.inject.Inject

class AlbumListRepositoryImpl@Inject constructor(
    private val albumListApiService: AlbumListApiService,
):AlbumListRepository  {
    override suspend fun getAlbumListData(id: Long):Result< AlbumListData> {
        return try {
            val response = albumListApiService.getAlbumListData(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null"))
                }
            } else {
                Result.failure(IOException("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("AlbumListRepoImpl", "Failed to get album list data", e)
            Result.failure(e)
        }
    }
}