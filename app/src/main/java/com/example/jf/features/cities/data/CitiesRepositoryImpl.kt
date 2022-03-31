package com.example.jf.features.cities.data

import com.example.jf.features.cities.data.mappers.CitiesMapper
import com.example.jf.features.cities.data.network.Api
import com.example.jf.features.cities.domain.CitiesRepository
import com.example.jf.features.cities.domain.model.City

class CitiesRepositoryImpl(
    private val api: Api,
    private val citiesMapper: CitiesMapper
) : CitiesRepository {
    override suspend fun getCities(coordinates: String): MutableList<City> {
        return citiesMapper.map(api.getCities(coordinates))
    }
}
