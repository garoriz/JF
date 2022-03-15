package com.example.jf.data

import com.example.jf.data.api.Api
import com.example.jf.data.api.response.cities.CitiesResponse
import com.example.jf.data.api.response.wiki.Wiki
import com.example.jf.domain.repository.CitiesRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CitiesRepositoryImpl(
    private val api: Api
) : CitiesRepository {
    override suspend fun getCities(): CitiesResponse {
        return api.getCities()
    }
}
