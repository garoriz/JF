package com.example.jf.features.articleAboutCity.data.api

import com.example.jf.features.articleAboutCity.data.api.response.CityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("w/api.php")
    suspend fun getCity(@Query("titles") title: String): CityResponse
}
