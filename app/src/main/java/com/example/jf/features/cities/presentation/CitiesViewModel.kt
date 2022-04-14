package com.example.jf.features.cities.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.cities.domain.entity.City
import com.example.jf.features.cities.domain.usecase.GetCitiesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase
) : ViewModel() {

    private var _cities: MutableLiveData<Result<MutableList<City>>> = MutableLiveData()
    val cities: LiveData<Result<MutableList<City>>> = _cities

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetCities(location: String) {
        viewModelScope.launch {
            try {
                val cities = getCitiesUseCase(location)
                _cities.value = Result.success(cities)
            } catch (ex: Exception) {
                _cities.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
