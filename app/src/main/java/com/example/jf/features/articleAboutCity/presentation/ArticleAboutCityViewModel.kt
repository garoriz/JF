package com.example.jf.features.articleAboutCity.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.articleAboutCity.domain.entity.City
import com.example.jf.features.articleAboutCity.domain.usecase.GetCityUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleAboutCityViewModel @Inject constructor(
    private val getCityUseCase: GetCityUseCase
) : ViewModel() {

    private var _city: MutableLiveData<Result<City>> = MutableLiveData()
    val city: LiveData<Result<City>> = _city

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetInfoAboutCity(name: String) {
        viewModelScope.launch {
            try {
                val city = getCityUseCase(name)
                _city.value = Result.success(city)
            } catch (ex: Exception) {
                _city.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
