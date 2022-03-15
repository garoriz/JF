package com.example.jf.data.api

import com.example.jf.data.api.response.cities.CitiesResponse
import com.example.jf.data.api.response.wiki.Wiki
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {
    @GET("cities")
    suspend fun getCities(): CitiesResponse
}
