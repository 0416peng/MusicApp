package com.example.musicapp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.song.SongDetailData
import com.example.data.repository.song.SongRepository
import com.example.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    val repository: SongRepository,
    val musicPlayerManager: MusicPlayerManager
) {
    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId
    private val _songDetail= MutableStateFlow<SongDetailData?>(null)
    val songDetailData = _songDetail.asStateFlow()
    val songsList=musicPlayerManager.songsList
    fun getSongDetail(){

    }
}