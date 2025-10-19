package com.example.data.model.search

data class SearchMVDetail(
    val code: Int,
    val result: MVResult
)

data class MVResult(
    val hlWords: Any,
    val mvCount: Int,
    val mvs: List<Mv>
)

data class Mv(
    val alg: String,
    val alias: List<String>,
    val arTransName: String,
    val artistId: Int,
    val artistName: String,
    val artists: List<MVArtist>,
    val briefDesc: Any,
    val cover: String,
    val desc: Any,
    val duration: Int,
    val id: Int,
    val mark: Int,
    val name: String,
    val playCount: Int,
    val transNames: List<String>
)

data class MVArtist(
    val alias: List<String>,
    val id: Int,
    val name: String,
    val transNames: List<String>
)