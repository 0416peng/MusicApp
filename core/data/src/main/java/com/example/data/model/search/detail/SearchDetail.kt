package com.example.data.model.search.detail

data class SearchDetail(
    val code: Int,
    val result: Result,
    val trp: Trp
)

data class Result(
    val album: Album,
    val artist: ArtistXX,
    val code: Int,
    val new_mlog: NewMlog,
    val order: List<String>,
    val playList: PlayList,
    val rec_query: List<Any?>,
    val sim_query: SimQuery,
    val song: Song,
    val user: User,
    val voice: Voice,
    val voicelist: Voicelist
)

data class Trp(
    val rules: List<String>
)

data class Album(
    val albums: List<AlbumX>,
    val more: Boolean,
    val moreText: String,
    val resourceIds: List<Int>
)

data class ArtistXX(
    val artists: List<ArtistXXX>,
    val more: Boolean,
    val moreText: String,
    val resourceIds: List<Int>
)

data class NewMlog(
    val more: Boolean,
    val resources: List<Any>
)

data class PlayList(
    val more: Boolean,
    val moreText: String,
    val playLists: List<PlayLists>,
    val resourceIds: List<Long>
)

data class SimQuery(
    val more: Boolean,
    val sim_querys: List<SimQueryX>
)

data class Song(
    val ksongInfos: KsongInfos,
    val more: Boolean,
    val moreText: String,
    val resourceIds: List<Long>,
    val songs: List<SongX>
)

data class User(
    val more: Boolean,
    val moreText: String,
    val resourceIds: List<Long>,
    val users: List<UserX>
)

class Voice

class Voicelist

data class AlbumX(
    val alg: String,
    val alias: List<Any?>,
    val artist: Artist,
    val artists: List<ArtistX>,
    val blurPicUrl: String,
    val briefDesc: String,
    val commentThreadId: String,
    val company: String,
    val companyId: Int,
    val copyrightId: Int,
    val description: String,
    val id: Int,
    val name: String,
    val onSale: Boolean,
    val paid: Boolean,
    val pic: Long,
    val picId: Long,
    val picId_str: String,
    val picUrl: String,
    val publishTime: Long,
    val size: Int,
    val status: Int,
    val tags: String,
    val transNames: List<String>,
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

data class ArtistXXX(
    val accountId: Int,
    val albumSize: Int,
    val alg: String,
    val alia: List<String>,
    val alias: List<String>,
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

data class PlayLists(
    val alg: String,
    val bookCount: Int,
    val coverImgUrl: String,
    val creator: Creator,
    val description: String,
    val highQuality: Boolean,
    val id: Long,
    val name: String,
    val officialTags: List<String>,
    val playCount: Int,
    val playlistType: String,
    val specialType: Int,
    val subscribed: Boolean,
    val track: Track,
    val trackCount: Int,
    val userId: Long
)

data class Creator(
    val authStatus: Int,
    val avatarUrl: String,
    val nickname: String,
    val userId: Long,
    val userType: Int
)

data class Track(
    val album: AlbumXX,
    val alias: List<String>,
    val artists: List<ArtistXXXXXX>,
    val bMusic: BMusic,
    val commentThreadId: String,
    val copyFrom: String,
    val copyright: Int,
    val copyrightId: Int,
    val dayPlays: Int,
    val disc: String,
    val duration: Int,
    val fee: Int,
    val ftype: Int,
    val hMusic: HMusic,
    val hearTime: Int,
    val id: Long,
    val lMusic: LMusic,
    val mMusic: MMusic,
    val mvid: Long,
    val name: String,
    val no: Int,
    val playedNum: Int,
    val popularity: Int,
    val position: Int,
    val ringtone: String,
    val rtUrls: List<Any?>,
    val rtype: Int,
    val score: Int,
    val starred: Boolean,
    val starredNum: Int,
    val status: Int,
    val transNames: List<String>
)

data class AlbumXX(
    val alias: List<String>,
    val artist: ArtistXXXXXX,
    val artists: List<ArtistXXXXXX>,
    val blurPicUrl: String,
    val briefDesc: String,
    val commentThreadId: String,
    val company: String,
    val companyId: Long,
    val copyrightId: Long,
    val description: String,
    val id: Long,
    val name: String,
    val onSale: Boolean,
    val pic: Long,
    val picId: Long,
    val picId_str: String,
    val picUrl: String,
    val publishTime: Long,
    val size: Int,
    val songs: List<Any?>,
    val status: Int,
    val tags: String,
    val type: String
)

data class ArtistXXXXXX(
    val albumSize: Int,
    val alias: List<Any>,
    val briefDesc: String,
    val id: Long,
    val img1v1Id: Long,
    val img1v1Url: String,
    val musicSize: Int,
    val name: String,
    val picId: Int,
    val picUrl: String,
    val topicPerson: Int,
    val trans: String
)

data class BMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class HMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class LMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class MMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class SimQueryX(
    val alg: String,
    val keyword: String
)

data class KsongInfos(
    val `1357375695`: X1357375695
)

data class SongX(
    val al: Al,
    val alg: String,
    val alia: List<Any?>,
    val ar: List<Ar>,
    val cd: String,
    val cf: String,
    val copyright: Int,
    val cp: Int,
    val djId: Int,
    val dt: Int,
    val fee: Int,
    val ftype: Int,
    val h: H,
    val hr: Hr,
    val id: Long,
    val l: L,
    val lyrics: String,
    val m: M,
    val mark: Long,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val officialTags: List<Any?>,
    val originCoverType: Int,
    val originSongSimpleData: OriginSongSimpleData,
    val pop: Int,
    val privilege: Privilege,
    val pst: Int,
    val publishTime: Long,
    val recommendText: String,
    val resourceState: Boolean,
    val rt: String,
    val rtUrls: List<Any?>,
    val rtype: Int,
    val s_id: Int,
    val showRecommend: Boolean,
    val single: Int,
    val specialTags: List<Any?>,
    val sq: Sq,
    val st: Int,
    val t: Int,
    val tns: List<String>,
    val v: Int,
    val version: Int
)

data class X1357375695(
    val accompanyId: String,
    val androidDownloadUrl: String,
    val deeplinkUrl: String
)

data class Al(
    val id: Int,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
)

data class Ar(
    val alia: List<String>,
    val alias: List<String>,
    val id: Int,
    val name: String,
    val tns: List<String>
)

data class H(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class Hr(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class L(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class M(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class OriginSongSimpleData(
    val albumMeta: AlbumMeta,
    val artists: List<ArtistXXXXXXX>,
    val name: String,
    val songId: Int
)

data class Privilege(
    val chargeInfoList: List<ChargeInfo>,
    val code: Int,
    val cp: Int,
    val cs: Boolean,
    val dl: Int,
    val dlLevel: String,
    val downloadMaxBrLevel: String,
    val downloadMaxbr: Int,
    val fee: Int,
    val fl: Int,
    val flLevel: String,
    val flag: Int,
    val freeTrialPrivilege: FreeTrialPrivilege,
    val id: Long,
    val maxBrLevel: String,
    val maxbr: Int,
    val payed: Int,
    val pl: Int,
    val plLevel: String,
    val playMaxBrLevel: String,
    val playMaxbr: Int,
    val preSell: Boolean,
    val rightSource: Int,
    val rscl: Int,
    val sp: Int,
    val st: Int,
    val subp: Int,
    val toast: Boolean
)

data class Sq(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class AlbumMeta(
    val id: Int,
    val name: String
)

data class ArtistXXXXXXX(
    val id: Int,
    val name: String
)

data class ChargeInfo(
    val chargeType: Int,
    val rate: Int
)

data class FreeTrialPrivilege(
    val cannotListenReason: Int,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)

data class UserX(
    val accountStatus: Int,
    val alg: String,
    val anchor: Boolean,
    val authStatus: Int,
    val authenticationTypes: Int,
    val authority: Int,
    val avatarDetail: AvatarDetail,
    val avatarImgId: Long,
    val avatarImgIdStr: String,
    val avatarImgId_str: String,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundImgIdStr: String,
    val backgroundUrl: String,
    val birthday: Int,
    val city: Int,
    val defaultAvatar: Boolean,
    val description: String,
    val detailDescription: String,
    val djStatus: Int,
    val followed: Boolean,
    val gender: Int,
    val mutual: Boolean,
    val nickname: String,
    val province: Int,
    val signature: String,
    val userId: Long,
    val userType: Int,
    val vipType: Int
)

data class AvatarDetail(
    val identityIconUrl: String,
    val identityLevel: Int,
    val userType: Int
)