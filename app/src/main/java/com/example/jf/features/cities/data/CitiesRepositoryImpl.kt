package com.example.jf.features.cities.data

import com.example.jf.features.cities.data.api.mappers.CitiesMapper
import com.example.jf.features.cities.data.api.Api
import com.example.jf.features.cities.domain.repository.CitiesRepository
import com.example.jf.features.cities.domain.entity.City
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val api: Api,
    private val citiesMapper: CitiesMapper
) : CitiesRepository {
    override suspend fun getCities(coordinates: String): MutableList<City> {
        return citiesMapper.map(api.getCities(coordinates))
    }
}
