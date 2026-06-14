package com.example.data.di

import android.util.Log
import java.io.IOException

suspend fun <T> apiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        val response = apiCall()
        if (response != null) {
            Result.success(response)
        } else {
            Result.failure(IOException("Response body is null"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

inline fun <reified T : Any> Result<T>.handleApi(
    tag: String,
    crossinline onError: (String) -> Unit,
    crossinline onSuccess: (T) -> Unit
) {
    this.fold(
        onSuccess = { data ->
            val code = try {
                val field = data::class.java.getDeclaredField("code")
                field.isAccessible = true
                val raw = field.get(data)
                when (raw) {
                    is Int -> raw
                    is Long -> raw.toInt()
                    else -> 200
                }
            } catch (_: Exception) {
                200
            }
            if (code == 200) {
                onSuccess(data)
            } else {
                val msg = "业务码异常: $code"
                Log.w(tag, msg)
                onError(msg)
            }
        },
        onFailure = { e ->
            val msg = "网络错误: ${e.message}"
            Log.e(tag, "请求失败", e)
            onError(msg)
        }
    )
}
