package com.example.albumList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.manager.MusicPlayerManager
import com.example.data.model.albumList.AlbumListData
import com.example.data.repository.albumList.AlbumListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val albumListRepository: AlbumListRepository,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId
    private val _albumListData = MutableStateFlow<AlbumListData?>(null)
    val albumListData = _albumListData.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()
    fun getAlbumList(id: Long) {
        Log.d("viewModel", id.toString())
        viewModelScope.launch {
            albumListRepository.getAlbumListData(id)
                .onSuccess { data ->
                    if (data.code == 200) {
                        _albumListData.value = data
                    } else {
                        // 服务器返回了成功响应，但业务码不为 200
                        _errorState.value = "推荐歌单加载失败，业务码：${data.code}"
                        Log.w("HomeViewModel", "getRecommendAlbum业务失败, code: ${data.code}")
                    }
                }
                .onFailure { exception ->
                    _errorState.value = "歌单列表加载失败: ${exception.message}"
                    Log.e("HomeViewModel", "getAlbumList网络或解析错误", exception)
                }
        }}
        fun onPlayPauseClicked(songId: Long) {
            musicPlayerManager.playSong(songId)
        }
        fun errorShown() {
            _errorState.value = null
        }

}
