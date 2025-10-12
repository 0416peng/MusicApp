package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.home.RecommendAlbumData
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


    fun getRecommendAlbum(limit: Int) {
        viewModelScope.launch {
            homeRepository.getRecommendAlbum(limit)
        }

    }
}