package com.example.data.repository.search

import com.example.data.apiService.search.SearchApiService
import com.example.data.di.apiCall
import com.example.data.model.search.HotSearchData
import com.example.data.model.search.SearchSuggestData
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRepository {
    override suspend fun getHotSearchData(): Result<HotSearchData> {
        return apiCall {
            searchApiService.getHotSearchData().body()!!
        }
    }

    override suspend fun getSearchSuggest(keywords: String): Result<SearchSuggestData> {
        return apiCall {
            searchApiService.getSearchSuggest(keywords).body()!!
        }
    }


}
