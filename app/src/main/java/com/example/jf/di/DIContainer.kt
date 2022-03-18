package com.example.jf.di

import com.example.jf.data.api.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://wft-geo-db.p.rapidapi.com/"
private const val QUERY_LANGUAGE_CODE = "languageCode"
private const val LANGUAGE_CODE = "ru"
private const val QUERY_LIMIT = "limit"
private const val LIMIT = "10"
private const val QUERY_RADIUS = "radius"
private const val RADIUS = "100"
private const val QUERY_SORT = "sort"
private const val SORT = "-population"
private const val QUERY_TYPES = "types"
private const val TYPES = "city"

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

    private val limitInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_LIMIT,
                LIMIT
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val radiusInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_RADIUS,
                RADIUS
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val sortInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_SORT,
                SORT
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val typesInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_TYPES,
                TYPES
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
            .addInterceptor(limitInterceptor)
            .addInterceptor(radiusInterceptor)
            .addInterceptor(sortInterceptor)
            .addInterceptor(typesInterceptor)
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
