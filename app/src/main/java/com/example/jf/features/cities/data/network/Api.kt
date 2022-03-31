package com.example.jf.features.cities.data.network

import com.example.jf.features.cities.data.network.response.CitiesResponse
import retrofit2.http.*

interface Api {
    @GET("v1/geo/locations/{coordinates}/nearbyCities")
    suspend fun getCities(@Path("coordinates") coordinates: String): CitiesResponse
}
