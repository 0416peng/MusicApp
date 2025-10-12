package com.example.data.apiService.auth

import com.example.data.model.auth.AuthStatue
import com.example.data.model.auth.VisitorLoginData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VisitorLoginApiService {
    @GET("register/anonimous")
    suspend fun visitorLogin(): Response<VisitorLoginData>
}