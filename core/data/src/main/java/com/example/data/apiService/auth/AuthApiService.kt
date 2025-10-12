package com.example.data.apiService.auth

import com.example.data.model.auth.AuthStatue
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {
    @GET("login/qr/check")
    suspend fun getStatue(@Query("key") key:String): Response<AuthStatue>
}