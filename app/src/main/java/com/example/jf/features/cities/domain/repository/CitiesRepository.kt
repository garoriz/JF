package com.example.jf.features.cities.domain.repository

import com.example.jf.features.cities.domain.entity.City

interface CitiesRepository {
    suspend fun getCities(coordinates: String): MutableList<City>
}
