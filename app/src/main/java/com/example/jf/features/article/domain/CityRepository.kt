package com.example.jf.features.article.domain

import com.example.jf.features.article.domain.model.City

interface CityRepository {
    suspend fun getCity(title: String): City
}
