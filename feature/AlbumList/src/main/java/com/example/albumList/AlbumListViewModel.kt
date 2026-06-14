package com.example.albumList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.handleApi
import com.example.data.model.albumList.AlbumListData
import com.example.data.model.song.SongsListData
import com.example.data.repository.albumList.AlbumListRepository
import com.example.player.MusicPlayerManager
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

    fun getAlbumList(id: Long) { viewModelScope.launch {
        albumListRepository.getAlbumListData(id).handleApi("AlbumListViewModel", { _errorState.value = it }) { _albumListData.value = it }
    } }

    fun onAddListClicked(index: Int) {
        val list = _albumListData.value?.songs?.map { SongsListData(it.id, it.name) } ?: return
        musicPlayerManager.addMultipleToQueue(list, index)
    }

    fun errorShown() { _errorState.value = null }
}
