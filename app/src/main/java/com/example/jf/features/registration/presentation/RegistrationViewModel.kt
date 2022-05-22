package com.example.jf.features.registration.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.post.domain.model.CurrentUser
import com.example.jf.features.registration.domain.model.User
import com.example.jf.features.registration.domain.useCases.AddUserInDbUseCase
import com.example.jf.features.registration.domain.useCases.CreateUserUseCase
import com.example.jf.features.settings.domain.useCases.ChangePasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val addUserInDbUseCase: AddUserInDbUseCase
) : ViewModel() {

    private var _uid: MutableLiveData<Result<String?>> = MutableLiveData()
    val uid: LiveData<Result<String?>> = _uid

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onCreateUser(email: String, password: String, nick: String) {
        viewModelScope.launch {
            try {
                val uid = createUserUseCase(email, password, nick)
                _uid.value = Result.success(uid)
            } catch (ex: Exception) {
                _uid.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onAddUserInDb(nick: String, uid: String) {
        viewModelScope.launch {
            try {
                addUserInDbUseCase(nick, uid)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
