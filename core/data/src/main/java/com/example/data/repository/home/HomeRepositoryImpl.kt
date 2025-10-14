package com.example.data.repository.home

import com.example.data.apiService.home.BannerApiService
import com.example.data.apiService.home.NewAlbumApiService
import com.example.data.apiService.home.RecommendAlbumApiService
import com.example.data.model.home.BannerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import java.io.IOException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val recommendAlbumApiService: RecommendAlbumApiService,
    private val newAlbumApiService: NewAlbumApiService,
    private val bannerApiService: BannerApiService
) : HomeRepository {
    override suspend fun getRecommendAlbum(limit: Int): RecommendAlbumData {
        val response= recommendAlbumApiService.getRecommendAlbum(limit)
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

    override suspend fun getNewAlbum(): NewAlbumData {
        val response= newAlbumApiService.getNewAlbum()
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

    override suspend fun getBanner(): BannerData {
        val response= bannerApiService.getBanner()
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
