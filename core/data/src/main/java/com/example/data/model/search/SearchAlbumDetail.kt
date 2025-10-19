package com.example.data.model.search

data class SearchAlbumDetail(
    val code: Int,
    val result: AlbumResult
)

data class AlbumResult(
    val albumCount: Int,
    val albums: List<AlbumX>,
    val hlWords: List<String>
)

data class AlbumX(
    val alg: String,
    val alias: List<String>,
    val artist: Artist,
    val artists: List<ArtistX>,
    val blurPicUrl: String,
    val briefDesc: String,
    val commentThreadId: String,
    val company: String,
    val companyId: Int,
    val containedSong: String,
    val copyrightId: Int,
    val description: String,
    val id: Int,
    val mark: Int,
    val name: String,
    val onSale: Boolean,
    val paid: Boolean,
    val pic: Long,
    val picId: Long,
    val picId_str: String,
    val picUrl: String,
    val publishTime: Long,
    val size: Int,
    val songs: Any,
    val status: Int,
    val tags: String,
    val type: String
)

data class Artist(
    val albumSize: Int,
    val alia: List<String>,
    val alias: List<String>,
    val briefDesc: String,
    val id: Int,
    val img1v1Id: Long,
    val img1v1Id_str: String,
    val img1v1Url: String,
    val musicSize: Int,
    val name: String,
    val picId: Long,
    val picId_str: String,
    val picUrl: String,
    val topicPerson: Int,
    val trans: String,
    val transNames: List<String>
)

data class ArtistX(
    val albumSize: Int,
    val alias: List<Any>,
    val briefDesc: String,
    val id: Int,
    val img1v1Id: Long,
    val img1v1Id_str: String,
    val img1v1Url: String,
    val musicSize: Int,
    val name: String,
    val picId: Int,
    val picUrl: String,
    val topicPerson: Int,
    val trans: String
)