package com.example.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.song.LyricData
import com.example.data.model.song.SongDetailData
import com.example.data.repository.song.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val musicPlayerManager: MusicPlayerManager
): ViewModel() {
    private val _lyricData = MutableStateFlow<LyricData?>(null)
    val lyricData = _lyricData.asStateFlow()
    private val _songDetail=MutableStateFlow<SongDetailData?>(null)
    val songDetail=_songDetail.asStateFlow()
    private val _errorState= MutableStateFlow<String?>(null)
    val errorState=_errorState.asStateFlow()
    val isPlaying =musicPlayerManager.isPlaying
    fun getSongLyric(id:Long){
        viewModelScope.launch {
            songRepository.getSongLyric(id)
                .onSuccess {
                    _lyricData.value=it
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
}