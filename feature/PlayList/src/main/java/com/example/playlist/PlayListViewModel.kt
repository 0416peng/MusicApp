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
    private companion object {
        const val SUCCESS_CODE = 200
        const val PAGE_SIZE = 50
    }

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
            handleRepositoryResult(
                result = playListRepository.getPlayListData(id, 0),
                actionName = "获取歌单列表",
                codeOf = { it.code }
            ) { data ->
                _playListData.value = data
                _currentOffset.value = PAGE_SIZE
            }
        }
    }

    fun loadMorePlayListData(id: Long) {
        viewModelScope.launch {
            if (_isRefreshing.value) return@launch
            _isRefreshing.value = true

            handleRepositoryResult(
                result = playListRepository.getPlayListData(id, _currentOffset.value),
                actionName = "加载更多歌曲",
                codeOf = { it.code }
            ) { newData ->
                val currentSongs = _playListData.value?.songs ?: emptyList()
                _playListData.value = _playListData.value?.copy(
                    songs = currentSongs + newData.songs
                )
                _currentOffset.value += PAGE_SIZE
            }

            _isRefreshing.value = false
        }
    }

    fun getPlayListDetail(id: Long) {
        viewModelScope.launch {
            handleRepositoryResult(
                result = playListRepository.getPlatListDetailData(id),
                actionName = "获取歌单详情",
                codeOf = { it.code }
            ) { data ->
                _playListDetailData.value = data
            }
        }
    }

    fun onAddListClicked(index: Int) {
        val songs = _playListData.value?.songs ?: return
        val list = songs.map { SongsListData(it.id, it.name) }
        musicPlayerManager.addMultipleToQueue(list, index)
    }

    fun errorShown() {
        _errorState.value = null
    }

    private inline fun <T> handleRepositoryResult(
        result: Result<T>,
        actionName: String,
        codeOf: (T) -> Int,
        onSuccess: (T) -> Unit
    ) {
        result.fold(
            onSuccess = { data ->
                val code = codeOf(data)
                if (code == SUCCESS_CODE) {
                    onSuccess(data)
                } else {
                    val errorMsg = "$actionName 失败, 业务码: $code"
                    _errorState.value = errorMsg
                    Log.w("PlayListViewModel", errorMsg)
                }
            },
            onFailure = { exception ->
                val errorMsg = "$actionName 失败: ${exception.message}"
                _errorState.value = errorMsg
                Log.e("PlayListViewModel", errorMsg, exception)
            }
        )
    }
}
