package com.example.data

import com.example.data.model.LoginKey
import retrofit2.http.GET

interface LoginApiService {
    @GET("/login/qr/key")
    suspend fun getKey(): LoginKey
}