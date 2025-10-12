package com.example.data.apiService.auth

import com.example.data.model.auth.LoginKey
import retrofit2.Response
import retrofit2.http.GET

interface LoginApiService {
    @GET("/login/qr/key")
    suspend fun getKey(): Response<LoginKey>
}