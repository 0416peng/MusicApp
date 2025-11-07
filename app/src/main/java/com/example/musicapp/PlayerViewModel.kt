package com.example.musicapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.song.SongDetailData
import com.example.data.repository.song.SongRepository
import com.example.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    val repository: SongRepository,
    val musicPlayerManager: MusicPlayerManager
): ViewModel() {
    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId
    val isPlaying =musicPlayerManager.isPlaying
    private val _songDetail= MutableStateFlow<SongDetailData?>(null)
    val songDetailData = _songDetail.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()
    val songsList=musicPlayerManager.songsList
    fun getSongDetail(id: Long){
        Log.d("getSongDetail","getSongDetail")
        viewModelScope.launch {
            repository.getSongDetail(id)
                .onSuccess {data->
                    if(data.code==200){
                        _songDetail.value=data
                    }else{
                        _errorState.value="获取歌曲详情失败"
                    }
                }
                .onFailure { exception ->
                    val errorMsg = "网络错误: ${exception.message}"
                    _errorState.value = errorMsg
                }
        }
    }
    fun playOrPauseSong(songId: Long) {
        musicPlayerManager.playOrPauseSong(songId)
    }
    fun addMultipleToQueue(startIndex: Int){
        musicPlayerManager.addMultipleToQueue(songsList.value!!.toList(),startIndex)
    }
}