package com.example.data.repository.search

import android.util.Log
import com.example.data.apiService.search.SearchDetailApiService
import com.example.data.model.search.SearchResult
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryDetailImpl @Inject constructor(
    private val searchDetailApiService: SearchDetailApiService
) : SearchDetailRepository {
    override suspend fun getSearchDetail(
        keywords: String,
        offset: Int,
        type: Int
    ): Result<SearchResult> {
        return try {
            val response = searchDetailApiService.getSearchDetail(keywords, offset, type = type)
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