package com.example.jf.di

import com.example.jf.data.api.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://wft-geo-db.p.rapidapi.com/v1/geo/"
private const val QUERY_LANGUAGE_CODE = "languageCode"
private const val LANGUAGE_CODE = "ru"

object DIContainer {

    private val languageCodeInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_LANGUAGE_CODE,
                LANGUAGE_CODE
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(languageCodeInterceptor)
            .apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
                        builder.header(
                            "x-rapidapi-key",
                            "755aad9229msh76281d57502db71p170b08jsn5226a1d7ae79"
                        )
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
            }
            .build()
    }

    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}
