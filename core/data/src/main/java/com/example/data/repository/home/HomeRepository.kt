package com.example.data.repository.home

import com.example.data.model.home.BannerData
import com.example.data.model.home.HotSingerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import com.example.data.model.home.TopListData

interface HomeRepository {
    suspend fun getRecommendAlbum(limit: Int): Result<RecommendAlbumData>
    suspend fun getNewAlbum(): Result<NewAlbumData>
    suspend fun getBanner(): Result<BannerData>
    suspend fun getHotSinger(): Result<HotSingerData>
    suspend fun getTopList(): Result<TopListData>
}


