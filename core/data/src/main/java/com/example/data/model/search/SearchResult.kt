package com.example.data.model.search

import com.example.data.model.search.detail.SearchDetail

sealed class SearchResult {
    data class Songs(val result: SearchSongsDetail): SearchResult()
    data class Albums(val result: SearchAlbumDetail): SearchResult()
    data class Singers(val result: SearchSingerDetail): SearchResult()
    data class MV(val result: SearchMVDetail): SearchResult()
    data class PlayLists(val result: SearchPlayListDetail): SearchResult()
    data class Detail(val result: SearchDetail): SearchResult()

}