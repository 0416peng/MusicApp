package com.example.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.manager.MusicPlayerManager
import com.example.data.model.playList.PlayListData
import com.example.data.model.playList.PlayListDetailData
import com.example.data.repository.playList.PlayListRepository
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
    val currentlyPlayingSongId=musicPlayerManager.currentlyPlayingSongId

    private val _playListData= MutableStateFlow<PlayListData?>(null)
    val playListData=_playListData.asStateFlow()
    private val _playListDetailData= MutableStateFlow<PlayListDetailData?>(null)
    val playListDetailData=_playListDetailData.asStateFlow()
    private val _currentOffset= MutableStateFlow(0)

    private val _isRefreshing= MutableStateFlow(false)
    val isRefreshing=_isRefreshing.asStateFlow()

    fun getPlayListData(id: Long){
        viewModelScope.launch {
            val data=playListRepository.getPlayListData(id,0)
            if(data.code==200){
                _playListData.value=data
                _currentOffset.value=50
            }
        }

    }
    fun loadMorePlayListData(id: Long){
        viewModelScope.launch {
            if (_isRefreshing.value) return@launch
            _isRefreshing.value=true
            try {
                val newData=playListRepository.getPlayListData(id,_currentOffset.value)
                val currentData=_playListData.value?.songs?:emptyList()
                val updatedData=currentData+newData.songs
                _playListData.value=_playListData.value?.copy(songs = updatedData)
                _currentOffset.value+=50

            }catch (e: Exception){
                Log.e("PlayListViewModel", "loadMorePlayListData: ${e.message}")
            }finally {
                _isRefreshing.value=false
            }
        }
    }
    fun getPlayListDetail(id: Long){
        viewModelScope.launch {
            val data=playListRepository.getPlatListDetailData(id)
            if(data.code==200){
                _playListDetailData.value=data
            }
        }
    }
    fun onPlayPauseClicked(songId: Long){
        musicPlayerManager.playSong(songId)
    }
}