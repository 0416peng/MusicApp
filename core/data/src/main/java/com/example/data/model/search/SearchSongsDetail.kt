package com.example.data.model.search

data class SearchSongsDetail(
    val code: Int,
    val result: SongsResult
)

data class SongsResult(
    val hasMore: Boolean,
    val songCount: Int,
    val songs: List<Song>
)

data class Song(
    val album: Album,
    val alias: List<String>,
    val artists: List<ArtistXX>,
    val copyrightId: Int,
    val duration: Int,
    val fee: Int,
    val ftype: Int,
    val id: Long,
    val mark: Long,
    val mvid: Int,
    val name: String,
    val rUrl: Any,
    val rtype: Int,
    val status: Int,
    val transNames: List<String>
)

data class Album(
    val alia: List<String>,
    val artist: ArtistX,
    val copyrightId: Int,
    val id: Int,
    val mark: Int,
    val name: String,
    val picId: Long,
    val publishTime: Long,
    val size: Int,
    val status: Int,
    val transNames: List<String>
)

data class ArtistXX(
    val albumSize: Int,
    val alias: List<Any>,
    val fansGroup: Any,
    val id: Int,
    val img1v1: Int,
    val img1v1Url: String,
    val name: String,
    val picId: Int,
    val picUrl: Any,
    val trans: Any
)