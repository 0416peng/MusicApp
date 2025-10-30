package com.example.artist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs
import com.example.data.repository.artist.ArtistRepository
import com.example.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {
    val currentlyPlayingSongId=musicPlayerManager.currentlyPlayingSongId
    private val _hotSongs = MutableStateFlow<ArtistHotSongs?>(null)
    val hotSongs = _hotSongs.asStateFlow()
    private val _songs = MutableStateFlow<ArtistSongs?>(null)
    val songs = _songs.asStateFlow()
    private val _detail = MutableStateFlow<ArtistDetail?>(null)
    val detail = _detail.asStateFlow()
    private val _currentOffset= MutableStateFlow(0)

    private val _isRefreshing= MutableStateFlow(false)
    val isRefreshing=_isRefreshing.asStateFlow()
    private val _errorState= MutableStateFlow<String?>(null)
    val errorState=_errorState.asStateFlow()
    fun getArtistDetail(id: Long) {
        viewModelScope.launch {
            artistRepository.getArtistDetail(id)
                .onSuccess { data ->
                    if (data.code == 200) {
                       _detail.value = data
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

    fun getArtistHotSongs(id: Long) {
        viewModelScope.launch {
            artistRepository.getArtistHotSongs(id)
                .onSuccess { data ->
                    if (data.code == 200) {
                        _hotSongs.value = data
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

    fun onPlayPauseClicked(index: Int) {
        /*TODO:待完善歌手页面之后再编写
        val list =_songs.value?.songs?.map {
            item->item.id
        }
        musicPlayerManager.addMultipleToQueue(list,index)*/
    }
}