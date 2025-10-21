package com.example.data.model.search



data class SearchSingerDetail(
    val code: Int,
    val result: Result
)

data class Result(
    val artistCount: Int,
    val artists: List<Artist>,
    val hasMore: Boolean,
    val hlWords: List<String>,
    val searchQcReminder: Any
)

data class Artist(
    val accountId: Long,
    val albumSize: Int,
    val alg: String,
    val alia: List<String>,
    val alias: List<String>,
    val fansGroup: Any,
    val followed: Boolean,
    val id: Int,
    val identityIconUrl: String,
    val img1v1: Long,
    val img1v1Url: String,
    val mvSize: Int,
    val name: String,
    val picId: Long,
    val picUrl: String,
    val trans: String,
    val transNames: List<String>
)