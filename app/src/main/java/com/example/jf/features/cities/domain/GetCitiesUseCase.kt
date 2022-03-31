package com.example.jf.features.cities.domain

import com.example.jf.features.cities.domain.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCitiesUseCase(
    private val citiesRepository: CitiesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(coordinates: String): MutableList<City> {
        return withContext(dispatcher) {
            citiesRepository.getCities(coordinates)
        }
    }
}
