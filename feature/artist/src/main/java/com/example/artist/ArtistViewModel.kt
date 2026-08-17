package com.example.artist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.handleApi
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.model.artist.ArtistSongs
import com.example.data.model.song.SongsListData
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
    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId
    private val _hotSongs = MutableStateFlow<ArtistHotSongs?>(null)
    val hotSongs = _hotSongs.asStateFlow()
    private val _songs = MutableStateFlow<ArtistSongs?>(null)
    val songs = _songs.asStateFlow()
    private val _detail = MutableStateFlow<ArtistDetail?>(null)
    val detail = _detail.asStateFlow()
    private val _currentOffset = MutableStateFlow(0)
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)


    fun getArtistDetail(id: Long) { viewModelScope.launch {
        artistRepository.getArtistDetail(id).handleApi("ArtistViewModel", { _errorState.value = it }) { _detail.value = it }
    } }

    fun getArtistHotSongs(id: Long) { viewModelScope.launch {
        artistRepository.getArtistHotSongs(id).handleApi("ArtistViewModel", { _errorState.value = it }) { _hotSongs.value = it }
    } }

    fun getArtistSongs(id: Long) { viewModelScope.launch {
        if (_isRefreshing.value || _songs.value?.more == false) return@launch
        _isRefreshing.value = true
        artistRepository.getArtistSongs(id, _currentOffset.value).handleApi("ArtistViewModel", { _errorState.value = it }) { data ->
            val existing = _songs.value
            _songs.value =
                existing?.copy(songs = existing.songs + data.songs, more = data.more, total = data.total)
                    ?: data
            _currentOffset.value += data.songs.size
        }
        _isRefreshing.value = false
    } }

    fun onAddListClicked(index: Int) {
        val list = _songs.value?.songs?.map { SongsListData(it.id, it.name) } ?: return
        musicPlayerManager.addMultipleToQueue(list, index)
    }
}
