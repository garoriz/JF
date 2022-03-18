package com.example.jf.data.api

import com.example.jf.data.api.response.cities.CitiesResponse
import retrofit2.http.*

interface Api {
    @GET("v1/geo/locations/{coordinates}/nearbyCities")
    suspend fun getCities(@Path("coordinates") coordinates: String): CitiesResponse
}
