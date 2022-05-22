package com.example.jf.features.other.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.other.domain.model.User
import com.example.jf.features.other.domain.useCases.GetCurrentUserUseCase
import com.example.jf.features.other.domain.useCases.SignOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class OtherViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    private var _currentUser: MutableLiveData<Result<User?>> = MutableLiveData()
    val currentUser: LiveData<Result<User?>> = _currentUser

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetUser() {
        viewModelScope.launch {
            try {
                val currentUser = getCurrentUserUseCase()
                _currentUser.value = Result.success(currentUser)
            } catch (ex: Exception) {
                _currentUser.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            try {
                signOutUseCase()
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
