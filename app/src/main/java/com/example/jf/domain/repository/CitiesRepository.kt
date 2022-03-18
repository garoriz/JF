package com.example.jf.domain.repository

import com.example.jf.data.api.response.cities.CitiesResponse

interface CitiesRepository {
    suspend fun getCities(coordinates: String): CitiesResponse
}
