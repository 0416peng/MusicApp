package com.example.data.model.artist

data class ArtistHotSongs(
    val code: Int,
    val more: Boolean,
    val songs: List<HotSong>
)

data class HotSong(
    val a: Any,
    val additionalTitle: String,
    val al: Al,
    val alia: List<String>,
    val ar: List<Ar>,
    val awardName: String,
    val awardTags: Any,
    val cd: String,
    val cf: String,
    val copyright: Int,
    val cp: Int,
    val crbt: Any,
    val displayTags: Any,
    val djId: Int,
    val dt: Int,
    val entertainmentTags: Any,
    val fee: Int,
    val ftype: Int,
    val h: H,
    val hr: Any,
    val id: Long,
    val l: L,
    val m: M,
    val mainTitle: String,
    val mark: Long,
    val markTags: List<Any>,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: Any,
    val originCoverType: Int,
    val originSongSimpleData: OriginSongSimpleData,
    val pop: Int,
    val privilege: Privilege,
    val pst: Int,
    val publishTime: Long,
    val resourceState: Boolean,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Int,
    val rurl: Any,
    val s_id: Int,
    val single: Int,
    val songJumpInfo: Any,
    val sq: Sq,
    val st: Int,
    val t: Int,
    val tagPicList: Any,
    val v: Int,
    val version: Int
)


data class OriginSongSimpleData(
    val albumMeta: AlbumMeta,
    val artists: List<Artist>,
    val name: String,
    val songId: Int
)


data class AlbumMeta(
    val id: Int,
    val name: String
)

data class Artist(
    val id: Int,
    val name: String
)



