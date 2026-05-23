package com.example.data.di

import java.io.IOException

suspend fun <T> apiCall(apiCall: suspend () -> T): Result<T>{
    return try{
        val response=apiCall()
        if(response!=null){
            Result.success(response)
        }else{
            Result.failure(IOException("Response body is null"))
        }

    }catch (e: Exception){
        Result.failure(e)
    }
    }
