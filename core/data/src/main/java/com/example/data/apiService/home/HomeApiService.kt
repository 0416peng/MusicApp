package com.example.data.apiService.home

import com.example.data.model.home.BannerData
import com.example.data.model.home.HotSingerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import com.example.data.model.home.TopListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET("banner")
    suspend fun getBanner(@Query("type") type: Int = 1): Response<BannerData>
    @GET("/top/artists")
    suspend fun getHotSinger(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 15
    ): Response<HotSingerData>
    @GET("album/newest")
    suspend fun getNewAlbum(): Response<NewAlbumData>
    @GET("personalized")
    suspend fun getRecommendAlbum(@Query("limit") limit: Int): Response<RecommendAlbumData>
    @GET("toplist")
    suspend fun getTopList(): Response<TopListData>
}