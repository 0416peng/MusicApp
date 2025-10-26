package com.example.data.repository.artist

import com.example.common.ApiResult
import com.example.data.apiService.artist.ArtistDetailApiService
import com.example.data.apiService.artist.ArtistHotSongsApiService
import com.example.data.apiService.artist.ArtistSongsApiService
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistDetailApiService: ArtistDetailApiService,
    private val artistHotSongsApiService: ArtistHotSongsApiService,
    private val artistSongsApiService: ArtistSongsApiService
): ArtistRepository {
    override fun getArtistDetail(id: Long): Flow<ApiResult<ArtistDetail>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = artistDetailApiService.getArtistDetail(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(ApiResult.Success(body))
                } else {
                    // 【修复】使用 emit 发射我们自定义的 Error 状态
                    emit(ApiResult.Error("Response body is null for getArtistDetail"))
                }
            } else {
                // 【修复】使用 emit 发射我们自定义的 Error 状态
                emit(ApiResult.Error("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            // 【修复】使用 emit 发射我们自定义的 Error 状态
            emit(ApiResult.Error(e.message ?: "Unknown error occurred"))
        }
    }

    // --- 已修复的 getArtistHotSongs ---
    override fun getArtistHotSongs(id: Long): Flow<ApiResult<ArtistHotSongs>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = artistHotSongsApiService.getArtistHotSongs(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(ApiResult.Success(body))
                } else {
                    // 【修复】使用 emit
                    emit(ApiResult.Error("Response body is null for getArtistHotSongs"))
                }
            } else {
                // 【修复】使用 emit
                emit(ApiResult.Error("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            // 【修复】使用 emit
            emit(ApiResult.Error(e.message ?: "Unknown error occurred"))
        }
    }

    // --- 已修复的 getArtistSongs ---
    override fun getArtistSongs(id: Long, offset: Int): Flow<ApiResult<ArtistSongs>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = artistSongsApiService.getArtistSongs(id, offset)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(ApiResult.Success(body))
                } else {
                    // 【修复】使用 emit
                    emit(ApiResult.Error("Response body is null for getArtistSongs"))
                }
            } else {
                // 【修复】使用 emit
                emit(ApiResult.Error("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            // 【修复】使用 emit
            emit(ApiResult.Error(e.message ?: "Unknown error occurred"))
        }
    }
}

