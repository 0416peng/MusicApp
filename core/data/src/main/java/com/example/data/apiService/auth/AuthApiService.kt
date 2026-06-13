package com.example.data.apiService.auth

import com.example.data.model.auth.AuthStatue
import com.example.data.model.auth.LoginKey
import com.example.data.model.auth.LoginPic
import com.example.data.model.auth.VisitorLoginData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {
    @GET("/login/qr/key")
    suspend fun getKey(
        @Query("timestamp") timestamp: Long = System.currentTimeMillis()
    ): Response<LoginKey>

    @GET("login/qr/create")
    suspend fun getPic(
        @Query("key") key: String,
        @Query("qrimg") qrimg: Boolean = true,
        @Query("timestamp") timestamp: Long = System.currentTimeMillis()
    ): Response<LoginPic>

    @GET("login/qr/check")
    suspend fun getStatue(
        @Query("key") key: String,
        @Query("noCookie") noCookie: Boolean? = null,
        @Query("timestamp") timestamp: Long = System.currentTimeMillis()
    ): Response<AuthStatue>

    @GET("register/anonimous")
    suspend fun visitorLogin(
        @Query("timestamp") timestamp: Long = System.currentTimeMillis()
    ): Response<VisitorLoginData>
}
