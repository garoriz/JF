package com.example.jf.features.article.di

import com.example.jf.features.article.data.network.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://ru.wikipedia.org/"
private const val QUERY_ACTION = "action"
private const val ACTION = "query"
private const val QUERY_PROP = "prop"
private const val PROP = "extracts|pageimages"
private const val QUERY_EXSENTENCES = "exsentences"
private const val EXSENTENCES = "10"
private const val QUERY_EXLIMIT = "exlimit"
private const val EXLIMIT = "1"
private const val QUERY_EXPLAINTEXT = "explaintext"
private const val EXPLAINTEXT = "1"
private const val QUERY_FORMATVERSION = "formatversion"
private const val FORMATVERSION = "2"
private const val QUERY_FORMAT = "format"
private const val FORMAT = "json"
private const val QUERY_PIPROP = "piprop"
private const val PIPROP = "original"

object DIContainer {

    private val actionInterceptorInfoAboutCity = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_ACTION,
                ACTION
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val propInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_PROP,
                PROP
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val exsentencesInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_EXSENTENCES,
                EXSENTENCES
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val exlimitInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_EXLIMIT,
                EXLIMIT
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val explainInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_EXPLAINTEXT,
                EXPLAINTEXT
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val formatversionInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_FORMATVERSION,
                FORMATVERSION
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val formatInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_FORMAT,
                FORMAT
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val pipropInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_PIPROP,
                PIPROP
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
            .addInterceptor(actionInterceptorInfoAboutCity)
            .addInterceptor(propInterceptor)
            .addInterceptor(exsentencesInterceptor)
            .addInterceptor(exlimitInterceptor)
            .addInterceptor(explainInterceptor)
            .addInterceptor(formatversionInterceptor)
            .addInterceptor(formatInterceptor)
            .addInterceptor(pipropInterceptor)
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
