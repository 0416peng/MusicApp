package com.example.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.handleApi
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

    fun getRecommendAlbum(limit: Int) { viewModelScope.launch {
        homeRepository.getRecommendAlbum(limit).handleApi("HomeViewModel", { _errorState.value = it }) { _recommendAlbum.value = it }
    } }

    fun getNewAlbum() { viewModelScope.launch {
        homeRepository.getNewAlbum().handleApi("HomeViewModel", { _errorState.value = it }) { _newAlbum.value = it }
    } }

    fun getBanner() { viewModelScope.launch {
        homeRepository.getBanner().handleApi("HomeViewModel", { _errorState.value = it }) { _banner.value = it }
    } }

    fun getHotSinger() { viewModelScope.launch {
        homeRepository.getHotSinger().handleApi("HomeViewModel", { _errorState.value = it }) { _hotSinger.value = it }
    } }

    fun getTopList() { viewModelScope.launch {
        homeRepository.getTopList().handleApi("HomeViewModel", { _errorState.value = it }) { _topList.value = it }
    } }

    fun onSearchTextChanged(text: String) { _searchText.value = text }
    fun errorShown() { _errorState.value = null }
}
