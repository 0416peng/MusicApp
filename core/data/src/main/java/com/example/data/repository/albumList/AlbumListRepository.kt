package com.example.data.repository.albumList

import com.example.data.model.albumList.AlbumListData

interface AlbumListRepository {
    suspend fun getAlbumListData(id: Long): Result<AlbumListData>
}