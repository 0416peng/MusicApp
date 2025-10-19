package com.example.data.di


import com.example.data.model.search.PlayListResult
import com.example.data.model.search.SearchAlbumDetail
import com.example.data.model.search.SearchMVDetail
import com.example.data.model.search.SearchPlayListDetail
import com.example.data.model.search.SearchResult
import com.example.data.model.search.SearchSingerDetail
import com.example.data.model.search.SearchSongsDetail
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SearchResultConverterFactory(private val gson: Gson): Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type != SearchResult::class.java) {
            return null
        }

        return SearchResultConverter(gson)
    }

    private class SearchResultConverter(private val gson: Gson) : Converter<ResponseBody, SearchResult> {
        override fun convert(value: ResponseBody): SearchResult? {
            val responseString = value.string()
            return when {
                responseString.contains("\"songs\"") -> {
                    val result = gson.fromJson(responseString, SearchSongsDetail::class.java)
                   SearchResult.Songs(result)
                }
                responseString.contains("\"albums\"") -> {
                    val result = gson.fromJson(responseString, SearchAlbumDetail::class.java)
                    SearchResult.Albums(result)
                }
                responseString.contains("\"artists\"") -> {
                    val result = gson.fromJson(responseString, SearchSingerDetail::class.java)
                    SearchResult.Singers(result)
                }
                responseString.contains("\"playlists\"") -> {
                    val result = gson.fromJson(responseString, SearchPlayListDetail::class.java)
                    SearchResult.PlayLists(result)
                }
                responseString.contains("\"mvs\"") -> {
                    val result = gson.fromJson(responseString, SearchMVDetail::class.java)
                    SearchResult.MV(result)
                }
                else -> null
            }
        }
    }
}