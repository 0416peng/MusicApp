package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.home.BannerData
import com.example.data.model.home.HotSingerData
import com.example.data.model.home.NewAlbumData
import com.example.data.model.home.RecommendAlbumData
import com.example.data.model.home.TopListData
import com.example.data.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _recommendAlbum = MutableStateFlow<RecommendAlbumData?>(null)
    val recommendAlbum = _recommendAlbum.asStateFlow()
    private val _newAlbum = MutableStateFlow<NewAlbumData?>(null)
    val newAlbum = _newAlbum.asStateFlow()
    private val _banner = MutableStateFlow<BannerData?>(null)
    val banner = _banner.asStateFlow()
    private val _hotSinger= MutableStateFlow<HotSingerData?>(null)
    val hotSinger= _hotSinger.asStateFlow()
    private val _topList= MutableStateFlow<TopListData?>(null)
    val topList= _topList.asStateFlow()



    fun getRecommendAlbum(limit: Int) {
        viewModelScope.launch {
            val data = homeRepository.getRecommendAlbum(limit)
            if (data.code == 200) {
                _recommendAlbum.value = data
            }
        }

    }

    fun getNewAlbum() {
        viewModelScope.launch {
            val data = homeRepository.getNewAlbum()
            if (data.code == 200) {
                _newAlbum.value = data
            }
        }
    }

    fun getBanner() {
        viewModelScope.launch {
            val data = homeRepository.getBanner()
            if (data.code == 200) {
                _banner.value = data
            }
        }
    }
    fun getHotSinger() {
        viewModelScope.launch {
            val data = homeRepository.getHotSinger()
            if (data.code == 200) {
                _hotSinger.value = data
            }
        }
    }
    fun getTopList() {
        viewModelScope.launch {
            val data = homeRepository.getTopList()
            if (data.code == 200) {
               _topList.value = data
            }
        }
    }
}
