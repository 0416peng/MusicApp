package com.example.data.repository.search

import com.example.data.model.search.HotSearchData
import com.example.data.model.search.SearchSuggestData

interface SearchRepository {
    suspend fun getHotSearchData(): Result<HotSearchData>
    suspend fun getSearchSuggest(keywords: String): Result<SearchSuggestData>

}


