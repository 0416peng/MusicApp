package com.example.data.repository.artist

import com.example.data.apiService.artist.ArtistDetailApiService
import com.example.data.apiService.artist.ArtistHotSongsApiService
import com.example.data.apiService.artist.ArtistSongsApiService
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs
import java.io.IOException
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistDetailApiService: ArtistDetailApiService,
    private val artistHotSongsApiService: ArtistHotSongsApiService,
    private val artistSongsApiService: ArtistSongsApiService
): ArtistRepository {
    override suspend fun getArtistDetail(id: Long): Result<ArtistDetail> {
        return  try {
            val response= artistDetailApiService.getArtistDetail(id)
            if (response.isSuccessful){
                val body= response.body()
                if(body!=null){
                    Result.success(body)
                }else{
                    Result.failure(IOException("Response body is null"))
                }
            }else{
                Result.failure(IOException("API Error: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getArtistHotSongs(id: Long): Result<ArtistHotSongs> {
        return  try {
            val response= artistHotSongsApiService.getArtistHotSongs(id)
            if (response.isSuccessful){
                val body= response.body()
                if(body!=null){
                    Result.success(body)
                }else{
                    Result.failure(IOException("Response body is null"))
                }
            }else{
                Result.failure(IOException("API Error: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getArtistSongs(id: Long,offset: Int): Result<ArtistSongs> {
        return  try {
            val response= artistSongsApiService.getArtistSongs(id,offset)
            if (response.isSuccessful){
                val body= response.body()
                if(body!=null){
                    Result.success(body)
                }else{
                    Result.failure(IOException("Response body is null"))
                }
            }else{
                Result.failure(IOException("API Error: ${response.code()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}