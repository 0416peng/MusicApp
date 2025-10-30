package com.example.data.di

import com.example.data.manager.UserSessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL="https://api-enhanced-henna.vercel.app/"
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        // 添加日志拦截器，方便在 Logcat 中查看网络请求和响应信息
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Singleton
    class AuthInterceptor @Inject constructor(
        private val userSessionManager: UserSessionManager // 注入 SessionManager
    ) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val cookie = runBlocking { userSessionManager.cookieFlow.first() }

            val originalRequest = chain.request()

            val requestBuilder = originalRequest.newBuilder()

            if (cookie != null) {
                requestBuilder.addHeader("Cookie", cookie)
            }
            val originalUrl = originalRequest.url
            val newUrl = originalUrl.newBuilder().addQueryParameter("randomCNIP","true").build()
            requestBuilder.url(newUrl)
            val newRequest = requestBuilder.build()
            return chain.proceed(newRequest)
        }
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(SearchResultConverterFactory(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}