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
    suspend fun getKey(): Response<LoginKey>

    @GET("login/qr/create")
    suspend fun getPic(
        @Query("key") key: String,
    ): Response<LoginPic>

    @GET("login/qr/check")
    suspend fun getStatue(@Query("key") key: String): Response<AuthStatue>

    @GET("register/anonimous")
    suspend fun visitorLogin(): Response<VisitorLoginData>
}
