package com.example.data.repository.playList

import com.example.data.di.apiCall
import com.example.data.apiService.playList.PlayListApiService
import com.example.data.model.playList.PlayListData
import com.example.data.model.playList.PlayListDetailData
import javax.inject.Inject


class PlayListRepositoryImpl @Inject constructor(
    private val playListApiService: PlayListApiService
) : PlayListRepository {
    override suspend fun getPlayListData(id: Long, offset: Int): Result<PlayListData> {
        return apiCall {
            playListApiService.getPlayListData(id, offset).body()!!
        }
    }


    override suspend fun getPlatListDetailData(id: Long): Result<PlayListDetailData> {
        return apiCall {
            playListApiService.getPlayListDetailData(id).body()!!
        }
    }
}
