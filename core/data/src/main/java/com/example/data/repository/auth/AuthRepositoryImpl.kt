package com.example.data.repository.auth

import com.example.data.apiService.auth.AuthApiService
import com.example.data.apiService.auth.LoginApiService
import com.example.data.apiService.auth.LoginPicApiService
import com.example.data.model.auth.AuthStatue
import com.example.data.model.auth.LoginKey
import com.example.data.model.auth.LoginPic
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val loginApiService: LoginApiService,private val loginPicApiService: LoginPicApiService,private val authApiService: AuthApiService) :
    AuthRepository {
    override suspend fun getKey(): LoginKey {
        return loginApiService.getKey()
    }

    override suspend fun getPic(key:String): LoginPic {
        return loginPicApiService.getPic(key)
    }

    override suspend fun getStatue(key: String): AuthStatue {
        return authApiService.getStatue(key)
    }

}