package com.example.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.di.handleApi
import com.example.data.model.search.HotSearchData
import com.example.data.model.search.SearchSuggestData
import com.example.data.repository.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.catch
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

    init {
        searchText
            .debounce(300)
            .map { it.trim() }
            .distinctUntilChanged()
            .flatMapLatest { keyword ->
                if (keyword.isBlank()) {
                    flowOf<Result<SearchSuggestData>?>(null)
                } else {
                    flow {
                        emit(searchRepository.getSearchSuggest(keyword))
                    }
                }
            }
            .onEach { result ->
                if (result == null) {
                    _searchSuggestData.value = null
                } else {
                    result.handleApi(
                        "SearchViewModel",
                        { _errorState.value = it }
                    ) {
                        _searchSuggestData.value = it
                    }
                }
            }
            .catch { exception ->
                _errorState.value = "网络错误: ${exception.message}"
            }
            .launchIn(viewModelScope)
    }

    fun getHotSearchData() { viewModelScope.launch {
        searchRepository.getHotSearchData().handleApi("SearchViewModel", { _errorState.value = it }) { _hotSearchData.value = it }
    } }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun clearSearchSuggest() { _searchSuggestData.value = null }
}
