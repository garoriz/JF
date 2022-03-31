package com.example.jf.features.article.data

import com.example.jf.features.article.data.mappers.CityMapper
import com.example.jf.features.article.data.network.Api
import com.example.jf.features.article.domain.CityRepository
import com.example.jf.features.article.domain.model.City

class CityRepositoryImpl(
    private val api: Api,
    private val cityMapper: CityMapper
) : CityRepository {
    override suspend fun getCity(title: String): City {
        return cityMapper.map(api.getCity(title))
    }
}
