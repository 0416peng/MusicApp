package com.example.data.repository.home

import com.example.data.apiService.home.RecommendAlbumApiService
import com.example.data.model.home.RecommendAlbumData
import java.io.IOException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val recommendAlbumApiService: RecommendAlbumApiService
) : HomeRepository {
    override suspend fun getRecommendAlbum(limit: Int): RecommendAlbumData {
        val response= recommendAlbumApiService.getStatue(limit)
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
