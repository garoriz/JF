package com.example.jf.features.article.domain

import com.example.jf.features.article.domain.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCityUseCase(
    private val cityRepository: CityRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(title: String): City {
        return withContext(dispatcher) {
            cityRepository.getCity(title)
        }
    }
}
