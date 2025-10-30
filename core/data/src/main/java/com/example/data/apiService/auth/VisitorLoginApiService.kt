package com.example.data.apiService.auth

import com.example.data.model.auth.VisitorLoginData
import retrofit2.Response
import retrofit2.http.GET

interface VisitorLoginApiService {
    @GET("register/anonimous")
    suspend fun visitorLogin(): Response<VisitorLoginData>
}