package com.example.data.repository.playList

import com.example.data.model.PlayListData

interface PlayListRepository {
    suspend fun getPlayListData(id: Long): PlayListData
}