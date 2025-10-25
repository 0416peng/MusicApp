package com.example.data.model.artist

data class ArtistSongs(
    val code: Int,
    val more: Boolean,
    val songs: List<Song>,
    val total: Int
)

data class Song(
    val a: Any,
    val al: Al,
    val alia: List<String>,
    val ar: List<Ar>,
    val cd: String,
    val cf: String,
    val cp: Int,
    val crbt: Any,
    val djId: Int,
    val dt: Int,
    val eq: String,
    val fee: Int,
    val ftype: Int,
    val h: H,
    val hr: Hr,
    val id: Int,
    val l: L,
    val m: M,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: NoCopyrightRcmd,
    val pop: Int,
    val privilege: Privilege,
    val pst: Int,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Int,
    val rurl: Any,
    val songJumpInfo: Any,
    val sq: Sq,
    val st: Int,
    val t: Int,
    val tns: List<String>,
    val v: Int
)

data class Al(
    val alia: List<String>,
    val id: Int,
    val name: String,
    val pic: Long,
    val pic_str: String,
    val tns: List<String>
)

data class Ar(
    val id: Int,
    val name: String
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

data class NoCopyrightRcmd(
    val expInfo: Any,
    val songId: String,
    val thirdPartySong: Any,
    val type: Int,
    val typeDesc: String
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
    val ignoreCache: String,
    val maxBrLevel: String,
    val maxbr: Int,
    val message: Any,
    val payed: Int,
    val pl: Int,
    val plLevel: String,
    val plLevels: Any,
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