package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.manager.MusicPlayerManager
import com.example.data.model.search.SearchAlbumDetail
import com.example.data.model.search.SearchMVDetail
import com.example.data.model.search.SearchPlayListDetail
import com.example.data.model.search.SearchResult
import com.example.data.model.search.SearchSingerDetail
import com.example.data.model.search.SearchSongsDetail
import com.example.data.model.search.detail.SearchDetail
import com.example.data.repository.search.SearchDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SearchCategory(val title: String,val type: Int)
@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val searchDetailRepository: SearchDetailRepository,
    private val musicPlayerManager: MusicPlayerManager
): ViewModel() {
    val categories = listOf(
        SearchCategory("综合", 1018),
        SearchCategory("单曲", 1),
        SearchCategory("歌单", 1000),
        SearchCategory("专辑", 10),
        SearchCategory("歌手", 100)
    )
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()
    private val _selectedCategoryIndex = MutableStateFlow(0)
    val selectedCategoryIndex = _selectedCategoryIndex.asStateFlow()

    private val _detailResult = MutableStateFlow<SearchDetail?>(null)
    val detailResult = _detailResult.asStateFlow()
    private val _songsResult = MutableStateFlow<SearchSongsDetail?>(null)
    val songsResult = _songsResult.asStateFlow()
    private val _albumsResult = MutableStateFlow<SearchAlbumDetail?>(null)
    val albumsResult = _albumsResult.asStateFlow()
    private val _playListsResult = MutableStateFlow<SearchPlayListDetail?>(null)
    val playListsResult = _playListsResult.asStateFlow()
    private val _mvsResult = MutableStateFlow<SearchMVDetail?>(null)
    val mvsResult = _mvsResult.asStateFlow()
    private val _singerResult = MutableStateFlow<SearchSingerDetail?>(null)
    val singerResult = _singerResult.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()
    private val _offset = MutableStateFlow(0)
    private val _searchKeyword = MutableStateFlow("")
    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId
    private fun fetchSearchResults(query: String, type: Int, isLoadMore: Boolean = false) {
        if (query.isBlank()) {
            return
        }
        _searchKeyword.value = query
        viewModelScope.launch {
            val currentOffset = if (isLoadMore) _offset.value else 0
            val result = searchDetailRepository.getSearchDetail(query, currentOffset, type)
            result.onSuccess { data ->
                when (data) {
                    is SearchResult.Songs -> {
                        if (data.result.code == 200) {
                            if (isLoadMore) {
                                _songsResult.value = _songsResult.value?.let { currentData ->
                                    currentData.copy(
                                        result = currentData.result.copy(
                                            songs = currentData.result.songs + data.result.result.songs
                                        )
                                    )
                                }
                            } else {
                                _songsResult.value = data.result
                            }
                                _offset.value = currentOffset + (data.result.result.songs.size)


                        } else {
                            _errorState.value = "获取歌曲列表失败, 业务码: ${data.result.code}"
                        }
                    }

                    is SearchResult.Albums -> {
                        if (data.result.code == 200) {
                            if (isLoadMore) {
                                _albumsResult.value = _albumsResult.value?.let { currentData ->
                                    currentData.copy(
                                        result = currentData.result.copy(
                                            albums = currentData.result.albums + data.result.result.albums
                                        )
                                    )
                                }
                            } else {
                                _albumsResult.value = data.result
                            }
                              _offset.value = currentOffset + (data.result.result.albums.size)
                        }
                            else {
                                _errorState.value = "获取专辑列表失败, 业务码: ${data.result.code}"
                            }

                    }
                    is SearchResult.Singers -> {
                        if (data.result.code == 200) {
                            if (isLoadMore) {
                                _singerResult.value = _singerResult.value?.let { currentData ->
                                    currentData.copy(
                                        result = currentData.result.copy(
                                            artists = currentData.result.artists + data.result.result.artists
                                        )
                                    )
                                }
                            } else {
                                _singerResult.value = data.result
                            }

                            _offset.value = currentOffset + (data.result.result.artists.size)

                        } else {
                            _errorState.value = "获取歌手列表失败, 业务码: ${data.result.code}"
                        }
                    }

                    is SearchResult.MV -> {
                        if (data.result.code == 200) {
                            if (isLoadMore) {
                                _mvsResult.value = _mvsResult.value?.let { currentData ->
                                    currentData.copy(
                                        result = currentData.result.copy(
                                            mvs = currentData.result.mvs + data.result.result.mvs
                                        )
                                    )
                                }
                            } else {
                                _mvsResult.value = data.result
                            }
                                _offset.value = currentOffset + (data.result.result.mvs.size)
                        } else {
                            _errorState.value = "获取MV列表失败, 业务码: ${data.result.code}"
                        }
                    }

                    is SearchResult.PlayLists -> {
                        if (data.result.code == 200) {
                            if (isLoadMore) {
                                _playListsResult.value =
                                    _playListsResult.value?.let { currentData ->
                                        currentData.copy(
                                            result = currentData.result.copy(
                                                playlists = currentData.result.playlists + data.result.result.playlists
                                            )
                                        )
                                    }
                            } else {
                                _playListsResult.value = data.result
                            }

                                _offset.value = currentOffset + (data.result.result.playlists.size)

                        } else {
                            _errorState.value = "获取歌单列表失败, 业务码: ${data.result.code}"
                        }
                    }

                    is SearchResult.Detail -> {
                        if (data.result.code == 200) {
                            _detailResult.value = data.result
                        } else {
                            _errorState.value = "获取综合列表失败, 业务码: ${data.result.code}"
                        }
                    }
                }
            }
                .onFailure {
                    val errorMsg = "网络错误: ${it.message}"
                    _errorState.value = errorMsg
                }
        }
    }

    fun onSearchTriggered(query: String) {
        fetchSearchResults(query, categories[_selectedCategoryIndex.value].type, isLoadMore = false)
    }

    fun loadMore() {
        val selectedCategory = categories[_selectedCategoryIndex.value]
        if (selectedCategory.type == 1018) return
        fetchSearchResults(_searchKeyword.value, selectedCategory.type, isLoadMore = true)
    }

    fun onTabSelected(index: Int) {
        _selectedCategoryIndex.value = index
        _offset.value = 0
        onSearchTriggered(_searchKeyword.value)
    }

    fun onPlayPauseClicked(songId: Long) {
        musicPlayerManager.playSong(songId)
    }


}