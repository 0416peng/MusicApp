package com.example.data.model.auth

data class LoginPic(
    val code: Int,
    val `data`: PicData
)

data class PicData(
    val qrimg: String,
    val qrurl: String
)