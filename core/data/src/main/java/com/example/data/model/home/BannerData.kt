package com.example.data.model.home

data class BannerData(
    val banners: List<Banner>,
    val code: Int,
    val trp: Trp
)

data class Banner(
    val alg: String,
    val bannerBizType: String,
    val bannerId: String,
    val encodeId: String,
    val exclusive: Boolean,
    val monitorClickList: List<Any>,
    val monitorImpressList: List<Any>,
    val pic: String,
    val requestId: String,
    val s_ctrp: String,
    val scm: String,
    val showAdTag: Boolean,
    val targetId: Long,
    val targetType: Int,
    val titleColor: String,
    val typeTitle: String,
    val url: String
)

data class Trp(
    val rules: List<String>
)