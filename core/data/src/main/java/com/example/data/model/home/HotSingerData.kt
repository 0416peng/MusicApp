package com.example.data.model.home

data class HotSingerData(
    val artists: List<HotArtistData>,
    val code: Int,
    val more: Boolean
)

data class HotArtistData(
    val accountId: Long,
    val albumSize: Int,
    val alg: Any,
    val alias: List<String>,
    val briefDesc: String,
    val fansCount: Int,
    val followed: Boolean,
    val id: Int,
    val identifyTag: Any,
    val img1v1Id: Long,
    val img1v1Id_str: String,
    val img1v1Url: String,
    val isSubed: Any,
    val musicSize: Int,
    val mvSize: Any,
    val name: String,
    val picId: Long,
    val picId_str: String,
    val picUrl: String,
    val publishTime: Any,
    val showPrivateMsg: Any,
    val topicPerson: Int,
    val trans: String,
    val transNames: List<String>
)