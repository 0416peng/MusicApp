package com.example.musicapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.handleApi
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
) : ViewModel() {
    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId
    val isPlaying = musicPlayerManager.isPlaying
    private val _songDetail = MutableStateFlow<SongDetailData?>(null)
    val songDetailData = _songDetail.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()
    val songsList = musicPlayerManager.songsList

    fun getSongDetail(id: Long) { viewModelScope.launch {
        repository.getSongDetail(id).handleApi("PlayerViewModel", { _errorState.value = it }) { _songDetail.value = it }
    } }

    fun playOrPauseSong(songId: Long) = musicPlayerManager.playOrPauseSong(songId)
    fun addMultipleToQueue(startIndex: Int) = musicPlayerManager.addMultipleToQueue(songsList.value!!.toList(), startIndex)
}
