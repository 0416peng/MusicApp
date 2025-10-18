package com.example.data.model.playList

data class PlayListDetailData(
    val code: Int,
    val fromUserCount: Int,
    val fromUsers: Any,
    val playlist: Playlist,
    val privileges: List<Privilege>,
    val relatedVideos: Any,
    val resEntrance: Any,
    val sharedPrivilege: Any,
    val songFromUsers: Any,
    val urls: Any
)

data class Playlist(
    val adType: Int,
    val algTags: Any,
    val backgroundCoverId: Int,
    val backgroundCoverUrl: Any,
    val bannedTrackIds: Any,
    val bizExtInfo: BizExtInfo,
    val cloudTrackCount: Int,
    val commentCount: Int,
    val commentThreadId: String,
    val copied: Boolean,
    val coverImgId: Long,
    val coverImgId_str: String,
    val coverImgUrl: String,
    val coverStatus: Int,
    val createTime: Long,
    val creator: Creator,
    val description: Any,
    val detailPageTitle: Any,
    val displayTags: Any,
    val displayUserInfoAsTagOnly: Boolean,
    val distributeTags: List<Any>,
    val englishTitle: Any,
    val gradeStatus: String,
    val highQuality: Boolean,
    val historySharedUsers: Any,
    val id: Long,
    val mixPodcastPlaylist: Boolean,
    val mvResourceInfos: Any,
    val name: String,
    val newDetailPageRemixVideo: Any,
    val newImported: Boolean,
    val officialPlaylistType: Any,
    val opRecommend: Boolean,
    val ordered: Boolean,
    val playCount: Long,
    val playlistType: String,
    val podcastTrackCount: Int,
    val privacy: Int,
    val relateResType: Any,
    val remixVideo: Any,
    val score: Any,
    val shareCount: Int,
    val sharedUsers: Any,
    val specialType: Int,
    val status: Int,
    val subscribed: Boolean,
    val subscribedCount: Int,
    val subscribers: List<Any>,
    val tags: List<Any>,
    val titleImage: Int,
    val titleImageUrl: Any,
    val trackCount: Int,
    val trackIds: List<TrackId>,
    val trackNumberUpdateTime: Long,
    val trackUpdateTime: Long,
    val tracks: List<Track>,
    val trialMode: Int,
    val updateFrequency: Any,
    val updateTime: Long,
    val userId: Long,
    val videoIds: Any,
    val videos: Any
)

data class Privilege(
    val bd: Any,
    val chargeInfoList: List<ChargeInfo>,
    val code: Int,
    val cp: Int,
    val cs: Boolean,
    val dl: Int,
    val dlLevel: String,
    val dlLevels: Any,
    val downloadMaxBrLevel: String,
    val downloadMaxbr: Int,
    val fee: Int,
    val fl: Int,
    val flLevel: String,
    val flag: Int,
    val freeTrialPrivilege: FreeTrialPrivilege,
    val id: Long,
    val ignoreCache: Any,
    val maxBrLevel: String,
    val maxbr: Int,
    val message: Any,
    val paidBigBang: Boolean,
    val payed: Int,
    val pc: Any,
    val pl: Int,
    val plLevel: String,
    val plLevels: Any,
    val playMaxBrLevel: String,
    val playMaxbr: Int,
    val preSell: Boolean,
    val realPayed: Int,
    val rightSource: Int,
    val rscl: Int,
    val sp: Int,
    val st: Int,
    val subp: Int,
    val toast: Boolean
)

class BizExtInfo

data class Creator(
    val accountStatus: Int,
    val anchor: Boolean,
    val authStatus: Int,
    val authenticationTypes: Int,
    val authority: Int,
    val avatarDetail: Any,
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
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val gender: Int,
    val mutual: Boolean,
    val nickname: String,
    val province: Int,
    val remarkName: Any,
    val signature: String,
    val userId: Long,
    val userType: Int,
    val vipType: Int
)

data class TrackId(
    val alg: Any,
    val at: Long,
    val dpr: Any,
    val f: Any,
    val id: Long,
    val rcmdReason: String,
    val rcmdReasonTitle: String,
    val sc: Any,
    val sr: Any,
    val t: Int,
    val tr: Int,
    val uid: Long,
    val v: Int
)

data class Track(
    val a: Any,
    val additionalTitle: Any,
    val al: Al,
    val alg: Any,
    val alia: List<Any>,
    val ar: List<Ar>,
    val awardTags: Any,
    val cd: String,
    val cf: String,
    val copyright: Int,
    val cp: Int,
    val crbt: Any,
    val displayReason: Any,
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
    val mainTitle: Any,
    val mark: Long,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: Any,
    val originCoverType: Int,
    val originSongSimpleData: Any,
    val pop: Int,
    val pst: Int,
    val pubDJProgramData: Any,
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

data class Al(
    val id: Int,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
)

data class Ar(
    val alias: List<Any>,
    val id: Int,
    val name: String,
    val tns: List<Any>
)

data class H(
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

data class Sq(
    val br: Int,
    val fid: Int,
    val size: Int,
    val sr: Int,
    val vd: Int
)

data class ChargeInfo(
    val chargeMessage: Any,
    val chargeType: Int,
    val chargeUrl: Any,
    val rate: Int
)

data class FreeTrialPrivilege(
    val cannotListenReason: Int,
    val freeLimitTagType: Any,
    val listenType: Any,
    val playReason: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)