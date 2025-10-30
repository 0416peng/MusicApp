package com.example.data.model.song

data class SongUrlData(
    val code: Int,
    val `data`: List<Data>
)

data class Data(
    val accompany: Any,
    val auEff: Any,
    val br: Int,
    val canExtend: Boolean,
    val channelLayout: Any,
    val closedGain: Int,
    val closedPeak: Int,
    val code: Int,
    val effectTypes: Any,
    val encodeType: Any,
    val expi: Int,
    val fee: Int,
    val flag: Int,
    val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
    val freeTrialInfo: String,
    val freeTrialPrivilege: FreeTrialPrivilege,
    val gain: Double,
    val id: Long,
    val immerseType: Any,
    val level: Any,
    val levelConfuse: Any,
    val md5: Any,
    val message: Any,
    val musicId: Any,
    val payed: Int,
    val peak: Any,
    val podcastCtrp: Any,
    val rightSource: Int,
    val size: Int,
    val sr: Int,
    val time: Int,
    val type: Any,
    val uf: Any,
    val url: String,
    val urlSource: Int
)

data class FreeTimeTrialPrivilege(
    val remainTime: Int,
    val resConsumable: Boolean,
    val type: Int,
    val userConsumable: Boolean
)

data class FreeTrialPrivilege(
    val cannotListenReason: Int,
    val freeLimitTagType: Any,
    val listenType: Any,
    val playReason: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)