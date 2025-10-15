package com.example.data.model.home

data class TopListData(
    val artistToplist: ArtistToplist,
    val code: Int,
    val list: List<Item0>
)

data class ArtistToplist(
    val coverUrl: String,
    val name: String,
    val position: Int,
    val upateFrequency: String,
    val updateFrequency: String
)

data class Item0(
    val ToplistType: String,
    val adType: Int,
    val algType: Any,
    val anonimous: Boolean,
    val artists: Any,
    val backgroundCoverId: Int,
    val backgroundCoverUrl: Any,
    val cloudTrackCount: Int,
    val commentThreadId: String,
    val coverImageUrl: Any,
    val coverImgId: Long,
    val coverImgId_str: String,
    val coverImgUrl: String,
    val coverText: Any,
    val createTime: Long,
    val creator: Any,
    val description: String,
    val englishTitle: Any,
    val highQuality: Boolean,
    val iconImageUrl: Any,
    val id: Long,
    val name: String,
    val newImported: Boolean,
    val opRecommend: Boolean,
    val ordered: Boolean,
    val originalCoverId: Int,
    val playCount: Long,
    val playlistType: String,
    val privacy: Int,
    val recommendInfo: Any,
    val socialPlaylistCover: Any,
    val specialType: Int,
    val status: Int,
    val subscribed: Any,
    val subscribedCount: Int,
    val subscribers: List<Any>,
    val tags: List<String>,
    val titleImage: Int,
    val titleImageUrl: Any,
    val topTrackIds: Any,
    val totalDuration: Int,
    val trackCount: Int,
    val trackNumberUpdateTime: Long,
    val trackUpdateTime: Long,
    val tracks: Any,
    val tsSongCount: Int,
    val uiPlaylistType: Any,
    val updateFrequency: String,
    val updateTime: Long,
    val userId: Long
)