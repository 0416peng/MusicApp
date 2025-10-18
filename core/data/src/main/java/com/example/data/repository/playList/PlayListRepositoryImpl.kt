package com.example.data.repository.playList

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
    override suspend fun getPlayListData(id: Long,offset:Int): PlayListData {
       return try {
            val response= playListApiService.getPlayListData(id, offset)
            if (response.isSuccessful){
                 response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getPlatListDetailData(id: Long): PlayListDetailData {
       return try {
            val response= playListDetailApiService.getPlayListDetailData(id)
            if (response.isSuccessful){
                 response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }
}