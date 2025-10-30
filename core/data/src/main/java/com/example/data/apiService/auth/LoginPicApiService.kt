package com.example.data.apiService.auth

import com.example.data.model.auth.LoginPic
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginPicApiService {
    @GET("login/qr/create")
    suspend fun getPic(
        @Query("key") key: String,
    ): Response<LoginPic>
}