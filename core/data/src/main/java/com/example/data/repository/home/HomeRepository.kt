package com.example.data.repository.home

import com.example.data.model.home.BannerData
import com.example.data.model.home.HotSingerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import com.example.data.model.home.TopListData

interface HomeRepository {
    suspend fun getRecommendAlbum(limit: Int): RecommendAlbumData
    suspend fun getNewAlbum(): NewAlbumData
    suspend fun getBanner(): BannerData
    suspend fun getHotSinger(): HotSingerData
    suspend fun getTopList(): TopListData
}


