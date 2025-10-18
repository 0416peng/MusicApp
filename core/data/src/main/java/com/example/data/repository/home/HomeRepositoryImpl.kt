package com.example.data.repository.home

import com.example.data.apiService.home.BannerApiService
import com.example.data.apiService.home.HotSingerApiService
import com.example.data.apiService.home.NewAlbumApiService
import com.example.data.apiService.home.RecommendAlbumApiService
import com.example.data.apiService.home.TopListApiService
import com.example.data.model.home.BannerData
import com.example.data.model.home.HotSingerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import com.example.data.model.home.TopListData
import java.io.IOException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val recommendAlbumApiService: RecommendAlbumApiService,
    private val newAlbumApiService: NewAlbumApiService,
    private val bannerApiService: BannerApiService,
    private val hotSingerApiService: HotSingerApiService,
    private val topListApiService: TopListApiService
) : HomeRepository {
    override suspend fun getRecommendAlbum(limit: Int): RecommendAlbumData {

      return  try {
          val response= recommendAlbumApiService.getRecommendAlbum(limit)
            if (response.isSuccessful){
                 response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getNewAlbum(): NewAlbumData {
       return try {
            val response= newAlbumApiService.getNewAlbum()
            if (response.isSuccessful){
                response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getBanner(): BannerData {
       return try {
            val response= bannerApiService.getBanner()
            if (response.isSuccessful){
                 response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getHotSinger(): HotSingerData {

       return try {
            val response= hotSingerApiService.getHotSinger()
            if (response.isSuccessful){
                 response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getTopList(): TopListData {

       return try {
            val response= topListApiService.getTopList()
            if (response.isSuccessful){
                 response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }

}
