package com.example.data.repository.search

import com.example.data.apiService.search.SearchApiService
import com.example.data.di.apiCall
import com.example.data.model.search.SearchResult
import javax.inject.Inject

class SearchRepositoryDetailImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchDetailRepository {
    override suspend fun getSearchDetail(
        keywords: String,
        offset: Int,
        type: Int
    ): Result<SearchResult> {
        return apiCall {
            searchApiService.getSearchDetail(keywords, offset, type = type).body()!!
        }
    }

}
