package com.example.data.repository.auth

import android.annotation.SuppressLint
import com.example.data.apiService.auth.AuthApiService
import com.example.data.apiService.auth.LoginApiService
import com.example.data.apiService.auth.LoginPicApiService
import com.example.data.apiService.auth.VisitorLoginApiService
import com.example.data.manager.UserSessionManager
import com.example.data.model.auth.AuthStatue
import com.example.data.model.auth.LoginKey
import com.example.data.model.auth.LoginPic
import com.example.data.model.auth.VisitorLoginData
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginApiService: LoginApiService,
    private val loginPicApiService: LoginPicApiService,
    private val authApiService: AuthApiService,
    private val visitorLoginApiService: VisitorLoginApiService,
    private val userSessionManager: UserSessionManager) :
    AuthRepository {
    @SuppressLint("SuspiciousIndentation")
    override suspend fun getKey(): LoginKey {
      val response = loginApiService.getKey()
        try{
            if (response.isSuccessful){
            return response.body()!!
        }else{
            throw IOException("API Error: ${response.code()}")
        }}catch (e: Exception){
            throw e
        }

    }

    override suspend fun getPic(key:String): LoginPic {
        val response = loginPicApiService.getPic(key)
        try {
            if (response.isSuccessful){
                return response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getStatue(key: String): AuthStatue {
        val response = authApiService.getStatue(key)
        try {
            if (response.isSuccessful){
                return response.body()!!
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }




    override suspend fun VisitorLogin(): VisitorLoginData {
        val response = visitorLoginApiService.visitorLogin()
        try {
            if (response.isSuccessful){
                return response.body()!!
                userSessionManager.saveCookie(response.body()!!.cookie)
            }else{
                throw IOException("API Error: ${response.code()}")
            }
        }catch (e: Exception){
            throw e
        }
    }



    }
