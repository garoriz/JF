package com.example.jf.features.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.settings.domain.useCases.ChangePasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {
    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onChangePassword(password: String) {
        viewModelScope.launch {
            try {
                changePasswordUseCase(password)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
