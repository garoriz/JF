package com.example.jf.domain.usecase

import com.example.jf.data.api.response.cities.CitiesResponse
import com.example.jf.data.api.response.cities.Data
import com.example.jf.domain.repository.CitiesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCitiesUseCase(
    private val citiesRepository: CitiesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(coordinates: String): CitiesResponse {
        return withContext(dispatcher) {
            citiesRepository.getCities(coordinates)
        }
    }
}
