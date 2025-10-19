package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.search.SearchAlbumDetail
import com.example.data.model.search.SearchMVDetail
import com.example.data.model.search.SearchPlayListDetail
import com.example.data.model.search.SearchResult
import com.example.data.model.search.SearchSingerDetail
import com.example.data.model.search.SearchSongsDetail
import com.example.data.repository.search.SearchDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val searchDetailRepository: SearchDetailRepository
): ViewModel(){
    private val _songsResult= MutableStateFlow<SearchSongsDetail?>(null)
    val songsResult=_songsResult.asStateFlow()
    private val _albumsResult= MutableStateFlow<SearchAlbumDetail?>(null)
    val albumsResult=_albumsResult.asStateFlow()
    private val _playListsResult= MutableStateFlow<SearchPlayListDetail?>(null)
    val playListsResult=_playListsResult.asStateFlow()
    private val _mvsResult= MutableStateFlow<SearchMVDetail?>(null)
    val mvsResult=_mvsResult.asStateFlow()
    private val _singerResult= MutableStateFlow<SearchSingerDetail?>(null)
    val singerResult=_singerResult.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()
    private val _offset= MutableStateFlow(0)
    fun onSearchTriggered(query:String,type: Int){
        if(query.isBlank()){
            return
        }
        viewModelScope.launch {
            val result=searchDetailRepository.getSearchDetail(query,_offset.value,type)
            result.onSuccess {data->
                when(data){
                    is SearchResult.Songs->{
                        if(data.result.code==200){
                            _songsResult.value=data.result
                        }else{
                            val errorMsg="获取歌曲列表失败, 业务码: ${data.result.code}"
                            _errorState.value=errorMsg
                        }
                    }
                    is SearchResult.Albums->{
                        if (data.result.code==200){
                            _albumsResult.value=data.result
                        }else{
                            val errorMsg="获取专辑列表失败, 业务码: ${data.result.code}"
                            _errorState.value=errorMsg
                        }
                    }
                    is SearchResult.Singers->{
                        if (data.result.code==200){
                            _singerResult.value=data.result
                        }else{
                            val errorMsg="获取歌手列表失败, 业务码: ${data.result.code}"
                            _errorState.value=errorMsg
                        }
                    }
                    is SearchResult.MV->{
                        if (data.result.code==200){
                            _mvsResult.value=data.result
                        }else{
                            val errorMsg="获取MV列表失败, 业务码: ${data.result.code}"
                            _errorState.value=errorMsg
                        }
                    }
                    is SearchResult.PlayLists->{
                        if (data.result.code==200){
                            _playListsResult.value=data.result
                        }else{
                            val errorMsg="获取歌单列表失败, 业务码: ${data.result.code}"
                            _errorState.value=errorMsg
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
}