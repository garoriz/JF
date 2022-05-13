package com.example.jf.features.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.login.domain.useCases.SignInAndGetIsCompletedUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val signInAndGetIsCompletedUseCase = SignInAndGetIsCompletedUseCase()

    private var _isCompleted: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompleted: LiveData<Result<Boolean>> = _isCompleted

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun signInAndGetIsCompleted(email: String, password: String) {
        viewModelScope.launch {
            try {
                val isCompleted = signInAndGetIsCompletedUseCase(email, password)
                _isCompleted.value = Result.success(isCompleted)
            } catch (ex: Exception) {
                _isCompleted.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
