package com.example.data.repository.search

import com.example.data.model.search.SearchResult

interface SearchDetailRepository {
    suspend fun getSearchDetail(keywords: String, offset: Int,type: Int): Result<SearchResult>
}