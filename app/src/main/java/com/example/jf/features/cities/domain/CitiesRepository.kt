package com.example.jf.features.cities.domain

import com.example.jf.features.cities.domain.model.City

interface CitiesRepository {
    suspend fun getCities(coordinates: String): MutableList<City>
}
