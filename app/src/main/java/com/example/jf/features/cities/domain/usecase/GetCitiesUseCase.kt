package com.example.jf.features.cities.domain.usecase

import com.example.jf.features.cities.domain.entity.City
import com.example.jf.features.cities.domain.repository.CitiesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(coordinates: String): MutableList<City> {
        return withContext(dispatcher) {
            citiesRepository.getCities(coordinates)
        }
    }
}
