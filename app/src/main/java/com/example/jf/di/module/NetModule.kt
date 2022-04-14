package com.example.jf.di.module

import com.example.jf.di.qualifier.*
import com.example.jf.features.articleAboutCity.data.api.Api
import dagger.Module
import dagger.Provides
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
private const val BASE_URL_CITIES = "https://wft-geo-db.p.rapidapi.com/"
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

@Module
class NetModule {

    @Provides
    @ActionInterceptor
    fun provideActionInterceptorInfoAboutCity() = Interceptor { chain ->
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

    @Provides
    @PropInterceptor
    fun providePropInterceptor() = Interceptor { chain ->
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

    @Provides
    @ExsentencesInterceptor
    fun provideExsentencesInterceptor() = Interceptor { chain ->
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

    @Provides
    @ExlimitInterceptor
    fun provideExlimitInterceptor() = Interceptor { chain ->
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

    @Provides
    @ExplainInterceptor
    fun provideExplainInterceptor() = Interceptor { chain ->
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

    @Provides
    @FormatversionInterceptor
    fun provideFormatversionInterceptor() = Interceptor { chain ->
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

    @Provides
    @FormatInterceptor
    fun provideFormatInterceptor() = Interceptor { chain ->
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

    @Provides
    @PipropInterceptor
    fun providePipropInterceptor() = Interceptor { chain ->
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

    @Provides
    @OkHttpInfoAboutCity
    fun getOkHttpInfoAboutCity(
        @ActionInterceptor provideActionInterceptor: Interceptor,
        @PropInterceptor providePropInterceptor: Interceptor,
        @ExsentencesInterceptor provideExsentencesInterceptor: Interceptor,
        @ExlimitInterceptor provideExlimitInterceptor: Interceptor,
        @ExplainInterceptor provideExplainInterceptor: Interceptor,
        @FormatversionInterceptor provideFormatversionInterceptor: Interceptor,
        @FormatInterceptor provideFormatInterceptor: Interceptor,
        @PipropInterceptor providerPipropInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(provideActionInterceptor)
        .addInterceptor(providePropInterceptor)
        .addInterceptor(provideExsentencesInterceptor)
        .addInterceptor(provideExlimitInterceptor)
        .addInterceptor(provideExplainInterceptor)
        .addInterceptor(provideFormatversionInterceptor)
        .addInterceptor(provideFormatInterceptor)
        .addInterceptor(providePipropInterceptor())
        .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideApiInfoAboutCity(
        @OkHttpInfoAboutCity provideOkHttpClient: OkHttpClient,
        provideGsonConverterFactory: GsonConverterFactory,
    ): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient)
        .addConverterFactory(provideGsonConverterFactory)
        .build()
        .create(Api::class.java)

    @Provides
    @LanguageCodeInterceptor
    fun provideLanguageCodeInterceptor() = Interceptor { chain ->
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

    @Provides
    @LimitInterceptor
    fun provideLimitInterceptor() = Interceptor { chain ->
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

    @Provides
    @RadiusInterceptor
    fun provideRadiusInterceptor() = Interceptor { chain ->
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

    @Provides
    @SortInterceptor
    fun provideSortInterceptor() = Interceptor { chain ->
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

    @Provides
    @TypesInterceptor
    fun provideTypesInterceptor() = Interceptor { chain ->
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

    @Provides
    @OkHttpCities
    fun getOkHttpCities(
        @LanguageCodeInterceptor provideLanguageCodeInterceptor: Interceptor,
        @LimitInterceptor provideLimitInterceptor: Interceptor,
        @RadiusInterceptor provideRadiusInterceptor: Interceptor,
        @SortInterceptor provideSortInterceptor: Interceptor,
        @TypesInterceptor provideTypesInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(provideLanguageCodeInterceptor)
        .addInterceptor(provideLimitInterceptor)
        .addInterceptor(provideRadiusInterceptor)
        .addInterceptor(provideSortInterceptor)
        .addInterceptor(provideTypesInterceptor)
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

    @Provides
    fun provideApiCities(
        @OkHttpCities getOkHttpCities: OkHttpClient,
        provideGsonConverterFactory: GsonConverterFactory,
    ): com.example.jf.features.cities.data.api.Api = Retrofit.Builder()
        .baseUrl(BASE_URL_CITIES)
        .client(getOkHttpCities)
        .addConverterFactory(provideGsonConverterFactory)
        .build()
        .create(com.example.jf.features.cities.data.api.Api::class.java)
}
