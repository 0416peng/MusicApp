package com.example.data.model.search


data class SearchPlayListDetail(
    val code: Int,
    val result: PlayListResult
)

data class PlayListResult(
    val hasMore: Boolean,
    val hlWords: List<String>,
    val playlistCount: Int,
    val playlists: List<Playlists>,
    val searchQcReminder: Any?
)

data class Playlists(
    val action: String,
    val actionType: String,
    val alg: String,
    val bookCount: Int,
    val coverImgUrl: String,
    val creator: Creator,
    val description: String,
    val highQuality: Boolean,
    val id: Long,
    val name: String,
    val officialPlaylistTitle: Any,
    val officialTags: List<String>,
    val playCount: Int,
    val playlistType: String,
    val recommendText: String,
    val score: Any,
    val specialType: Int,
    val subscribed: Boolean,
    val trackCount: Int,
    val userId: Long
)

data class Creator(
    val authStatus: Int,
    val avatarUrl: String,
    val expertTags: Any,
    val experts: Any,
    val nickname: String,
    val userId: Long,
    val userType: Int
)