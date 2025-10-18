package com.example.data.repository.AlbumList

import com.example.data.model.AlbumList.AlbumListData

interface AlbumListRepository {
    suspend fun getAlbumListData(id: Long): Result<AlbumListData>
}