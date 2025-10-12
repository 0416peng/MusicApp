package com.example.data.repository.home

import com.example.data.model.home.RecommendAlbumData

interface HomeRepository {
    suspend fun getRecommendAlbum(limit: Int): RecommendAlbumData
}