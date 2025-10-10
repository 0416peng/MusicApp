package com.example.data.model.auth

data class LoginKey(
    val code: Int,
    val `data`: KeyData
)

data class KeyData(
    val code: Int,
    val unikey: String
)