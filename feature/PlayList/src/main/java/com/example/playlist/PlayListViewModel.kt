package com.example.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

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

    fun getPlayListData(id: Long) {
        viewModelScope.launch {
            playListRepository.getPlayListData(id, 0)
                .onSuccess { data ->
                    if (data.code == 200) {
                        _playListData.value = data
                        _currentOffset.value = 50
                    } else {
                        val errorMsg = "获取歌单列表失败, 业务码: ${data.code}"
                        _errorState.value = errorMsg
                        Log.w("PlayListViewModel", errorMsg)
                    }
                }
                .onFailure { exception ->
                    val errorMsg = "网络错误: ${exception.message}"
                    _errorState.value = errorMsg
                    Log.e("PlayListViewModel", "getPlayListData 失败", exception)
                }
        }
    }

    fun loadMorePlayListData(id: Long) {
        viewModelScope.launch {
            if (_isRefreshing.value) return@launch
            _isRefreshing.value = true

            playListRepository.getPlayListData(id, _currentOffset.value)
                .onSuccess { newData ->
                    if (newData.code == 200) {
                        val currentSongs = _playListData.value?.songs ?: emptyList()
                        val updatedSongs = currentSongs + newData.songs
                        _playListData.value = _playListData.value?.copy(songs = updatedSongs)
                        _currentOffset.value += 50
                    } else {
                        _errorState.value = "加载更多失败, 业务码: ${newData.code}"
                    }
                }
                .onFailure { exception ->
                    _errorState.value = "加载更多失败: ${exception.message}"
                    Log.e("PlayListViewModel", "loadMorePlayListData 失败", exception)
                }

            _isRefreshing.value = false
        }
    }

    fun getPlayListDetail(id: Long) {
        viewModelScope.launch {
            playListRepository.getPlatListDetailData(id)
                .onSuccess { data ->
                    if (data.code == 200) {
                        _playListDetailData.value = data
                    } else {
                        val errorMsg = "获取歌单详情失败, 业务码: ${data.code}"
                        _errorState.value = errorMsg
                        Log.w("PlayListViewModel", errorMsg)
                    }
                }
                .onFailure { exception ->
                    val errorMsg = "网络错误: ${exception.message}"
                    _errorState.value = errorMsg
                    Log.e("PlayListViewModel", "getPlayListDetail 失败", exception)
                }
        }
    }

    fun onAddListClicked(index: Int) {
        val list=mutableListOf<SongsListData>()
        for(i in _playListData.value!!.songs){
            list.add(SongsListData(i.id,i.name))
        }
        musicPlayerManager.addMultipleToQueue(list, index)
    }

    // 提供一个方法让 UI 在显示错误后可以重置状态
    fun errorShown() {
        _errorState.value = null
    }
}