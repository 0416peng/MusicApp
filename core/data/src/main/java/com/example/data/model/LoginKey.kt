package com.example.data.model

data class LoginKey(
    val code: Int,
    val `data`: Data
)

data class Data(
    val code: Int,
    val unikey: String
)