package com.example.data.repository.albumList

import com.example.data.apiService.albumList.AlbumListApiService
import com.example.data.di.apiCall
import com.example.data.model.albumList.AlbumListData
import javax.inject.Inject

class AlbumListRepositoryImpl @Inject constructor(
    private val albumListApiService: AlbumListApiService,
) : AlbumListRepository {
    override suspend fun getAlbumListData(id: Long): Result<AlbumListData> {
        return apiCall {
            albumListApiService.getAlbumListData(id).body()!!
        }
    }
}
