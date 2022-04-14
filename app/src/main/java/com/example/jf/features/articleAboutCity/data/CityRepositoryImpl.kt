package com.example.jf.features.articleAboutCity.data

import com.example.jf.features.articleAboutCity.data.api.mappers.CityMapper
import com.example.jf.features.articleAboutCity.data.api.Api
import com.example.jf.features.articleAboutCity.domain.repository.CityRepository
import com.example.jf.features.articleAboutCity.domain.entity.City
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val api: Api,
    private val cityMapper: CityMapper
) : CityRepository {
    override suspend fun getCity(title: String): City {
        return cityMapper.map(api.getCity(title))
    }
}
