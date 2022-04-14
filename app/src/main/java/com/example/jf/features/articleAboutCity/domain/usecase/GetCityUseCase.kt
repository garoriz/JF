package com.example.jf.features.articleAboutCity.domain.usecase

import com.example.jf.features.articleAboutCity.domain.entity.City
import com.example.jf.features.articleAboutCity.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(title: String): City {
        return withContext(dispatcher) {
            cityRepository.getCity(title)
        }
    }
}
