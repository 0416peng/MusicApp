package com.example.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.search.HotSearchData
import com.example.data.model.search.SearchSuggestData
import com.example.data.repository.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _hotSearchData = MutableStateFlow<HotSearchData?>(null)
    val hotSearchData = _hotSearchData.asStateFlow()
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchSuggestData = MutableStateFlow<SearchSuggestData?>(null)
    val searchSuggestData = _searchSuggestData.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()


    fun getHotSearchData() {
        viewModelScope.launch {
            searchRepository.getHotSearchData()
                .onSuccess { data ->
                    if (data.code == 200) {
                        _hotSearchData.value = data
                    } else {
                        val errorMsg = "获取热门搜索列表失败, 业务码: ${data.code}"
                        Log.w("SearchViewModel", errorMsg)
                        _errorState.value = errorMsg
                    }
                }
                .onFailure { exception ->
                    val errorMsg = "网络错误: ${exception.message}"
                    _errorState.value = errorMsg
                }
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
        viewModelScope.launch {
            searchRepository.getSearchSuggest(text)
                .onSuccess { data ->
                    if (data.code == 200) {
                        _searchSuggestData.value = data
                    } else {
                        val errorMsg = "获取搜索建议列表失败, 业务码: ${data.code}"
                        Log.w("SearchViewModel", errorMsg)
                    }
                }
                .onFailure { exception ->
                    val errorMsg = "网络错误: ${exception.message}"
                    _errorState.value = errorMsg
                }
        }
    }

    fun clearSearchSuggest() {
        _searchSuggestData.value = null
    }
}