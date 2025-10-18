package com.example.data.repository.playList

import android.util.Log
import com.example.data.apiService.playList.PlayListApiService
import com.example.data.apiService.playList.PlayListDetailApiService
import com.example.data.model.playList.PlayListData
import com.example.data.model.playList.PlayListDetailData
import java.io.IOException
import javax.inject.Inject


class PlayListRepositoryImpl @Inject constructor(
    private val playListApiService: PlayListApiService,
    private val playListDetailApiService: PlayListDetailApiService
) : PlayListRepository {
    override suspend fun getPlayListData(id: Long, offset: Int): Result<PlayListData> {
        return try {
            val response = playListApiService.getPlayListData(id, offset)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null for getPlayListData"))
                }
            } else {
                Result.failure(IOException("API Error in getPlayListData: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("PlayListRepoImpl", "Failed to get playlist data", e)
            Result.failure(e)
        }
    }


    override suspend fun getPlatListDetailData(id: Long): Result<PlayListDetailData> {
        return try {
            val response = playListDetailApiService.getPlayListDetailData(id)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null for getPlatListDetailData"))
                }
            } else {
                Result.failure(IOException("API Error in getPlatListDetailData: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("PlayListRepoImpl", "Failed to get playlist detail data", e)
            Result.failure(e)
        }
    }
}