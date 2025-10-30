package com.example.home

import android.util.Log
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
    private val _hotSinger = MutableStateFlow<HotSingerData?>(null)
    val hotSinger = _hotSinger.asStateFlow()
    private val _topList = MutableStateFlow<TopListData?>(null)
    val topList = _topList.asStateFlow()
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()


    fun getRecommendAlbum(limit: Int) {
        viewModelScope.launch {
            homeRepository.getRecommendAlbum(limit)
                .onSuccess { data ->
                    if (data.code == 200) {
                        _recommendAlbum.value = data
                    } else {
                        _errorState.value = "推荐歌单加载失败，业务码：${data.code}"
                        Log.w("HomeViewModel", "getRecommendAlbum业务失败, code: ${data.code}")
                    }
                }
                .onFailure { exception ->
                    _errorState.value = "推荐歌单加载失败: ${exception.message}"
                    Log.e("HomeViewModel", "getRecommendAlbum网络或解析错误", exception)
                }
        }
    }

    fun getNewAlbum() {
        viewModelScope.launch {
            homeRepository.getNewAlbum()
                .onSuccess { data ->
                    if (data.code == 200) {
                        _newAlbum.value = data
                    } else {
                        _errorState.value = "新碟上架加载失败，业务码：${data.code}"
                        Log.w("HomeViewModel", "getNewAlbum业务失败, code: ${data.code}")
                    }
                }
                .onFailure { exception ->
                    _errorState.value = "新碟上架加载失败: ${exception.message}"
                    Log.e("HomeViewModel", "getNewAlbum网络或解析错误", exception)
                }
        }
    }

    fun getBanner() {
        viewModelScope.launch {
            homeRepository.getBanner()
                .onSuccess { data ->
                    if (data.code == 200) {
                        _banner.value = data
                    } else {
                        _errorState.value = "轮播图加载失败，业务码：${data.code}"
                        Log.w("HomeViewModel", "getBanner业务失败, code: ${data.code}")
                    }
                }
                .onFailure { exception ->
                    _errorState.value = "轮播图加载失败: ${exception.message}"
                    Log.e("HomeViewModel", "getBanner网络或解析错误", exception)
                }
        }
    }

    fun getHotSinger() {
        viewModelScope.launch {
            homeRepository.getHotSinger()
                .onSuccess { data ->
                    if (data.code == 200) {
                        _hotSinger.value = data
                    } else {
                        _errorState.value = "热门歌手加载失败，业务码：${data.code}"
                        Log.w("HomeViewModel", "getHotSinger业务失败, code: ${data.code}")
                    }
                }
                .onFailure { exception ->
                    _errorState.value = "热门歌手加载失败: ${exception.message}"
                    Log.e("HomeViewModel", "getHotSinger网络或解析错误", exception)
                }
        }
    }

    fun getTopList() {
        viewModelScope.launch {
            homeRepository.getTopList()
                .onSuccess { data ->
                    if (data.code == 200) {
                        _topList.value = data
                    } else {
                        _errorState.value = "排行榜加载失败，业务码：${data.code}"
                        Log.w("HomeViewModel", "getTopList业务失败, code: ${data.code}")
                    }
                }
                .onFailure { exception ->
                    _errorState.value = "排行榜加载失败: ${exception.message}"
                    Log.e("HomeViewModel", "getTopList网络或解析错误", exception)
                }
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    // 提供一个方法让 UI 在显示错误后可以重置状态
    fun errorShown() {
        _errorState.value = null
    }
}