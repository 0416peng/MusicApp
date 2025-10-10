package com.example.data.repository.auth

import com.example.data.model.auth.AuthStatue
import com.example.data.model.auth.LoginKey
import com.example.data.model.auth.LoginPic

interface AuthRepository {
    suspend fun getKey(): LoginKey
    suspend fun getPic(key: String): LoginPic
    suspend fun getStatue(key: String): AuthStatue
}