package com.example.data.repository.search

import android.util.Log
import com.example.data.apiService.search.HotSearchApiService
import com.example.data.apiService.search.SearchDetailApiService
import com.example.data.apiService.search.SearchSuggestApiService
import com.example.data.model.search.HotSearchData
import com.example.data.model.search.SearchSuggestData
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val hotSearchApiService: HotSearchApiService,
    private val searchSuggestApiService: SearchSuggestApiService,
    private val searchDetailApiService: SearchDetailApiService
) : SearchRepository {
    override suspend fun getHotSearchData(): Result<HotSearchData> {
        return try {
            val response = hotSearchApiService.getHotSearchData()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null for getPlayListData"))
                }
            } else {
                Result.failure(IOException("API Error in getPlayListData: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("PlayListRepoImpl", "Failed to get playlist data", e)
            Result.failure(e)
        }
    }

    override suspend fun getSearchSuggest(keywords: String): Result<SearchSuggestData> {
        return try {
            val response = searchSuggestApiService.getSearchSuggest(keywords)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Response body is null for getPlayListData"))
                }
            } else {
                Result.failure(IOException("API Error in getPlayListData: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("PlayListRepoImpl", "Failed to get playlist data", e)
            Result.failure(e)
        }
    }


}