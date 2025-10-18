package com.example.data.manager

import com.example.data.model.playList.TrackId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicPlayerManager @Inject constructor(){
    private val _currentlyPlayingSongId= MutableStateFlow<Long?>(null)
    val currentlyPlayingSongId=_currentlyPlayingSongId.asStateFlow()
    fun onTrackChanged(songId: Long) {
        _currentlyPlayingSongId.value = songId
    }
    fun onPlayerPaused(){
        _currentlyPlayingSongId.value=null
    }
    fun playSong(songId: Long){
        if(_currentlyPlayingSongId.value==songId){
            onPlayerPaused()
        }else{
            onTrackChanged(songId)
        }
    }

}