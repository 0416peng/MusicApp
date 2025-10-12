package com.example.data.model.auth

data class VisitorLoginData(
    val code: Int,
    val cookie: String,
    val createTime: Long,
    val userId: Long
)