package com.choidh.imagesearch.api

import com.choidh.imagesearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {
    companion object {
        const val BASE_URL = "https://openapi.naver.com/v1/"
        private const val CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        private const val CLIENT_SECRET = BuildConfig.NAVER_CLIENT_SECRET

        private val headerInterceptor = Interceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("X-Naver-Client-Id", NaverApi.CLIENT_ID)
                .addHeader("X-Naver-Client-Secret", NaverApi.CLIENT_SECRET)
                .build()
            return@Interceptor it.proceed(request)
        }

        fun client(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .build()
        }
    }

    @GET("search/image")
    suspend fun searchImages(
        @Query("query") keyword: String,
        @Query("start") page: Int,
        @Query("display") perPage: Int
    ): NaverResponse
}