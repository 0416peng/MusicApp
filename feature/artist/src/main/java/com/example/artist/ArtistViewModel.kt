package com.example.artist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ApiResult
import com.example.data.manager.MusicPlayerManager
import com.example.data.model.artist.ArtistDetail
import com.example.data.model.artist.ArtistHotSongs
import com.example.data.repository.artist.ArtistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface ArtistUiState {
    data object Loading : ArtistUiState // 加载中
    data class Success(
        val detail: ArtistDetail?,
        val hotSongs: ArtistHotSongs?
    ) : ArtistUiState // 成功获取数据
    data class Error(val message: String?) : ArtistUiState // 发生错误
}
@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val musicPlayerManager: MusicPlayerManager,
   private val savedStateHandle: SavedStateHandle
) : ViewModel() {
  /*  val currentlyPlayingSongId=musicPlayerManager.currentlyPlayingSongId
    private val _hotSongs = MutableStateFlow<ArtistHotSongs?>(null)
    val hotSongs = _hotSongs.asStateFlow()
    private val _songs = MutableStateFlow<ArtistSongs?>(null)
    val songs = _songs.asStateFlow()
    private val _detail = MutableStateFlow<ArtistDetail?>(null)
    val detail = _detail.asStateFlow()
    private val _currentOffset= MutableStateFlow(0)

    private val _isRefreshing= MutableStateFlow(false)
    val isRefreshing=_isRefreshing.asStateFlow()
    private val _errorState= MutableStateFlow<String?>(null)
    val errorState=_errorState.asStateFlow()
    fun getArtistDetail(id: Long) {
        viewModelScope.launch {
            artistRepository.getArtistDetail(id)
                .onSuccess { data ->
                    if (data.code == 200) {
                       _detail.value = data
                    } else {
                        val errorMsg = "获取歌单列表失败, 业务码: ${data.code}"
                        _errorState.value = errorMsg
                        Log.w("PlayListViewModel", errorMsg)
                    }
                }
                .onFailure { exception ->
                    val errorMsg = "网络错误: ${exception.message}"
                    _errorState.value = errorMsg
                    Log.e("PlayListViewModel", "getPlayListData 失败", exception)
                }
        }
    }

    fun getArtistHotSongs(id: Long) {
        viewModelScope.launch {
            artistRepository.getArtistHotSongs(id)
                .onSuccess { data ->
                    if (data.code == 200) {
                        _hotSongs.value = data
                    } else {
                        val errorMsg = "获取歌单列表失败, 业务码: ${data.code}"
                        _errorState.value = errorMsg
                        Log.w("PlayListViewModel", errorMsg)
                    }
                }
                .onFailure { exception ->
                    val errorMsg = "网络错误: ${exception.message}"
                    _errorState.value = errorMsg
                    Log.e("PlayListViewModel", "getPlayListData 失败", exception)
                }
        }
    }

    fun onPlayPauseClicked(songId: Long) {
        musicPlayerManager.playSong(songId)
    }
*/
  private val _uiState = MutableStateFlow<ArtistUiState>(ArtistUiState.Loading)
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

    // 2. 播放器相关的状态保持不变
    val currentlyPlayingSongId = musicPlayerManager.currentlyPlayingSongId

    init {
        // ViewModel初始化时，从SavedStateHandle获取ID并加载所有需要的数据
        val artistId: Long? = savedStateHandle["id"]
        if (artistId != null && artistId > 0) {
            loadAllArtistData(artistId)
        } else {
            _uiState.value = ArtistUiState.Error("无效的歌手ID")
        }
    }

    /**
     * 加载该艺术家的所有初始数据（详情和热门歌曲）。
     */
    private fun loadAllArtistData(id: Long) {
        viewModelScope.launch {
            // 使用 aunchIn 和 onEach 来订阅数据流
            // --- 加载歌手详情 ---
            artistRepository.getArtistDetail(id)
                .onEach { result ->
                    handleArtistDetailResult(result)
                }.launchIn(this) // 在当前viewModelScope启动收集

            // --- 加载歌手热门歌曲 ---
            artistRepository.getArtistHotSongs(id)
                .onEach { result ->
                    handleArtistHotSongsResult(result)
                }.launchIn(this) // 在当前viewModelScope启动收集
        }
    }

    private fun handleArtistDetailResult(result: ApiResult<ArtistDetail>) {
        when (result) {
            is ApiResult.Loading -> {
                // 如果当前不是Success状态，则显示加载中
                if (_uiState.value !is ArtistUiState.Success) {
                    _uiState.value = ArtistUiState.Loading
                }
            }
            is ApiResult.Success -> {
                // 更新UI状态，如果已经是Success，则在现有基础上更新detail
                _uiState.update { currentState ->
                    if (currentState is ArtistUiState.Success) {
                        currentState.copy(detail = result.data)
                    } else {
                        ArtistUiState.Success(detail = result.data, hotSongs = null)
                    }
                }
            }
            is ApiResult.Error -> {
                _uiState.value = ArtistUiState.Error(result.message)
            }
        }
    }

    private fun handleArtistHotSongsResult(result: ApiResult<ArtistHotSongs>) {
        when (result) {
            is ApiResult.Loading -> {
                if (_uiState.value !is ArtistUiState.Success) {
                    _uiState.value = ArtistUiState.Loading
                }
            }
            is ApiResult.Success -> {
                _uiState.update { currentState ->
                    if (currentState is ArtistUiState.Success) {
                        currentState.copy(hotSongs = result.data)
                    } else {
                        ArtistUiState.Success(detail = null, hotSongs = result.data)
                    }
                }
            }
            is ApiResult.Error -> {
                _uiState.value = ArtistUiState.Error(result.message)
            }
        }
    }


    /**
     * 处理播放/暂停点击事件。
     * 注意：playSong方法可能需要一个URL，这里假设它只需要ID。
     * 如果需要URL，你需要先获取它。
     */
    fun onPlayPauseClicked(songId: Long) {
        musicPlayerManager.playSong(songId)
    }}