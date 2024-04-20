package com.example.hw6_youtube_app.data.service


import com.example.hw6_youtube_app.BuildConfig
import com.example.hw6_youtube_app.data.api.YouTubeApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkService {

    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createOkHttpClient(interceptor: HttpLoggingInterceptor) =
        OkHttpClient().newBuilder()
            .addNetworkInterceptor(keyInterceptor)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    fun createLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = Level.BODY
        }

    fun createYouTubeApiService(retrofit: Retrofit): YouTubeApiService =
        retrofit.create(YouTubeApiService::class.java)

    private val keyInterceptor = Interceptor {
        val originalRequest = it.request()
        val newHttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        it.proceed(newRequest)
    }
}
