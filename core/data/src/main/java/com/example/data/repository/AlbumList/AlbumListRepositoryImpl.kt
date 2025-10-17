package com.example.data.repository.AlbumList

import android.util.Log
import com.example.data.apiService.AlbumList.AlbumListApiService
import com.example.data.model.AlbumListData
import java.io.IOException
import javax.inject.Inject

class AlbumListRepositoryImpl@Inject constructor(
    private val albumListApiService: AlbumListApiService,
):AlbumListRepository  {
    override suspend fun getAlbumListData(id: Long): AlbumListData {
        Log.d("repository",id.toString())
        val response = albumListApiService.getAlbumListData(id)
        try{
            if (response.isSuccessful){
                return response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }}catch (e: Exception){
            throw e
        }
    }
}