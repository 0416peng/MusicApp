package com.example.data.repository.home

import android.annotation.SuppressLint
import com.example.data.apiService.home.HomeApiService
import com.example.data.di.apiCall
import com.example.data.model.home.BannerData
import com.example.data.model.home.HotSingerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import com.example.data.model.home.TopListData
import java.io.IOException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApiService: HomeApiService
) : HomeRepository {
    @SuppressLint("SuspiciousIndentation")
    override suspend fun getRecommendAlbum(limit: Int): Result<RecommendAlbumData> {


        return apiCall{
            homeApiService.getRecommendAlbum(limit).body()!!
        }
    }

    override suspend fun getNewAlbum(): Result<NewAlbumData> {
        return apiCall{
          homeApiService.getNewAlbum().body()!!
        }
    }

    override suspend fun getBanner(): Result<BannerData> {

        return apiCall{
            homeApiService.getBanner().body()!!
        }
    }

    override suspend fun getHotSinger(): Result<HotSingerData> {

        return apiCall{
            homeApiService.getHotSinger().body()!!
        }
    }

    override suspend fun getTopList(): Result<TopListData> {

        return apiCall{
            homeApiService.getTopList().body()!!
        }
    }

}
