package com.example.data.repository.AlbumList

import com.example.data.model.AlbumListData

interface AlbumListRepository {
    suspend fun getAlbumListData(id: Int): AlbumListData
}