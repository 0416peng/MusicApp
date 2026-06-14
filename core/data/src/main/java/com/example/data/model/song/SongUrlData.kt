package com.example.data.model.song

data class SongUrlData(
    val code: Int,
    val `data`: List<Data>
)

data class Data(
    val accompany: Any,
    val auEff: Any,
    val br: Any,
    val canExtend: Boolean,
    val channelLayout: Any,
    val closedGain: Any,
    val closedPeak: Any,
    val code: Int,
    val effectTypes: Any,
    val encodeType: Any,
    val expi: Any,
    val fee: Any,
    val flag: Any,
    val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
    val freeTrialInfo: Any,
    val freeTrialPrivilege: FreeTrialPrivilege,
    val gain: Double,
    val id: Long,
    val immerseType: Any,
    val level: Any,
    val levelConfuse: Any,
    val md5: Any,
    val message: Any,
    val musicId: Any,
    val payed: Any,
    val peak: Any,
    val podcastCtrp: Any,
    val rightSource: Any,
    val size: Any,
    val sr: Any,
    val time: Any,
    val type: Any,
    val uf: Any,
    val url: String,
    val urlSource: Any
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
