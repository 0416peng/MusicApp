package com.example.data.repository.home

import com.example.data.model.home.BannerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData

interface HomeRepository {
    suspend fun getRecommendAlbum(limit: Int): RecommendAlbumData
    suspend fun getNewAlbum(): NewAlbumData
    suspend fun getBanner(): BannerData
}


