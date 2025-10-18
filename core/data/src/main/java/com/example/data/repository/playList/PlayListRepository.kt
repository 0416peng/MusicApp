package com.example.data.repository.playList

import com.example.data.model.playList.PlayListData
import com.example.data.model.playList.PlayListDetailData

interface PlayListRepository {
    suspend fun getPlayListData(id: Long,offset: Int=0): PlayListData
    suspend fun getPlatListDetailData(id: Long): PlayListDetailData
}