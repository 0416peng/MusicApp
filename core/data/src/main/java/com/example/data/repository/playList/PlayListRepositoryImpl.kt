package com.example.data.repository.playList

import com.example.data.apiService.playList.PlayListApiService
import com.example.data.model.PlayListData
import java.io.IOException
import javax.inject.Inject


class PlayListRepositoryImpl @Inject constructor(
    private val playListApiService: PlayListApiService
) : PlayListRepository {
    override suspend fun getPlayListData(id: Long): PlayListData {
        val response= playListApiService.getPlayListData(id)
        try {
            if (response.isSuccessful){
                return response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }
}