package com.example.player

import android.media.MediaDrm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.song.LyricData
import com.example.data.model.song.LyricLine
import com.example.data.model.song.SongDetailData
import com.example.data.model.song.parseLrc
import com.example.data.repository.song.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val musicPlayerManager: MusicPlayerManager
): ViewModel() {
    private val songList=musicPlayerManager.songsList
    private val _lyricData = MutableStateFlow<LyricData?>(null)
    val lyricData = _lyricData.asStateFlow()
    private val _parsedLyrics = MutableStateFlow<List<LyricLine>>(emptyList())
    val parsedLyrics = _parsedLyrics.asStateFlow()
    private val _songDetail=MutableStateFlow<SongDetailData?>(null)
    val songDetail=_songDetail.asStateFlow()
    private val _errorState= MutableStateFlow<String?>(null)
    val errorState=_errorState.asStateFlow()
    val isPlaying =musicPlayerManager.isPlaying
    val playbackProgress=musicPlayerManager.playbackProgress
    val currentSongDuration=musicPlayerManager.currentSongDuration
    val currentlyPlayingSongId=musicPlayerManager.currentlyPlayingSongId

    val currentLyricIndex: StateFlow<Int> = playbackProgress.map { currentProgress ->
        _parsedLyrics.value.indexOfLast { line ->
            line.time <= currentProgress
        }.coerceAtLeast(0)
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun getSongLyric(id:Long){
        viewModelScope.launch {
            songRepository.getSongLyric(id)
                .onSuccess {
                    _lyricData.value=it
                    _parsedLyrics.value= parseLrc(it.lrc.lyric)
                }
                .onFailure {
                    _errorState.value=it.message
                }
        }
    }
    fun getSongDetail(id: Long){
        viewModelScope.launch {
            songRepository.getSongDetail(id)
                .onSuccess {
                    _songDetail.value=it
                }
                .onFailure {
                    _errorState.value=it.message
                }
        }
    }
    fun playOrPauseSong(songId: Long) {
        musicPlayerManager.playOrPauseSong(songId)
    }
    fun addMultipleToQueue(Index: Int){
        musicPlayerManager.addMultipleToQueue(songList.value!!.toList(),Index)
    }
    fun seekTo(sliderPosition: Long){
        musicPlayerManager.seekTo(sliderPosition)
    }
}