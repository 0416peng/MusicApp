package com.example.albumList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.manager.MusicPlayerManager
import com.example.data.model.AlbumList.AlbumListData
import com.example.data.repository.AlbumList.AlbumListRepository
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

    val currentlyPlayingSongId=musicPlayerManager.currentlyPlayingSongId
    private val _albumListData= MutableStateFlow<AlbumListData?>(null)
    val albumListData=_albumListData.asStateFlow()

    fun getAlbumList(id: Long){
        Log.d("viewModel",id.toString())
        viewModelScope.launch {
           val data= albumListRepository.getAlbumListData(id)
            if(data.code==200){
                _albumListData.value=data
            }
        }
    }
  fun onPlayPauseClicked(songId: Long) {
        musicPlayerManager.playSong(songId)
    }
    }
