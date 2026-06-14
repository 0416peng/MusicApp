package com.example.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.handleApi
import com.example.data.model.playList.PlayListData
import com.example.data.model.playList.PlayListDetailData
import com.example.data.model.song.SongsListData
import com.example.data.repository.playList.PlayListRepository
import com.example.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    private val playListRepository: PlayListRepository,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {
    private companion object { const val PAGE_SIZE = 50 }

    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId
    private val _playListData = MutableStateFlow<PlayListData?>(null)
    val playListData = _playListData.asStateFlow()
    private val _playListDetailData = MutableStateFlow<PlayListDetailData?>(null)
    val playListDetailData = _playListDetailData.asStateFlow()
    private val _currentOffset = MutableStateFlow(0)
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    fun getPlayListData(id: Long) { viewModelScope.launch {
        playListRepository.getPlayListData(id, 0).handleApi("PlayListViewModel", { _errorState.value = it }) {
            _playListData.value = it; _currentOffset.value = PAGE_SIZE
        }
    } }

    fun loadMorePlayListData(id: Long) { viewModelScope.launch {
        if (_isRefreshing.value) return@launch
        _isRefreshing.value = true
        playListRepository.getPlayListData(id, _currentOffset.value).handleApi("PlayListViewModel", { _errorState.value = it }) { newData ->
            val currentSongs = _playListData.value?.songs ?: emptyList()
            _playListData.value = _playListData.value?.copy(songs = currentSongs + newData.songs)
            _currentOffset.value += PAGE_SIZE
        }
        _isRefreshing.value = false
    } }

    fun getPlayListDetail(id: Long) { viewModelScope.launch {
        playListRepository.getPlatListDetailData(id).handleApi("PlayListViewModel", { _errorState.value = it }) { _playListDetailData.value = it }
    } }

    fun onAddListClicked(index: Int) {
        val songs = _playListData.value?.songs ?: return
        musicPlayerManager.addMultipleToQueue(songs.map { SongsListData(it.id, it.name) }, index)
    }

    fun errorShown() { _errorState.value = null }
}
