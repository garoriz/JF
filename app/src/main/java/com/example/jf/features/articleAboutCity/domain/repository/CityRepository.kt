package com.example.jf.features.articleAboutCity.domain.repository

import com.example.jf.features.articleAboutCity.domain.entity.City

interface CityRepository {
    suspend fun getCity(title: String): City
}
