package com.example.jf.features.editProfile.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.editProfile.domain.models.User
import com.example.jf.features.editProfile.domain.usecases.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel  @Inject constructor(
    private val getUser: GetUserUseCase,
    private val updateNickUseCase: UpdateNickUseCase,
    private val updateNickInDbUseCase: UpdateNickInDbUseCase,
    private val uploadAvatarAndGetIsCompletedUseCase: UploadAvatarAndGetIsCompletedUseCase,
    private val getDownloadAvatarUriUseCase: GetDownloadAvatarUriUseCase,
    private val updateAvatarAndGetIsCompletedUseCase: UpdateAvatarUriAndGetIsCompletedUseCase,
    private val updateAvatarUriInDbUseCase: UpdateAvatarUriInDbUseCase,
) : ViewModel() {
    private var _currentUser: MutableLiveData<Result<User?>> = MutableLiveData()
    val currentUser: LiveData<Result<User?>> = _currentUser

    private var _isCompletedUpdatingNick: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompletedUpdatingNick: LiveData<Result<Boolean>> = _isCompletedUpdatingNick

    private var _isCompletedUploadingAvatar: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompletedUploadingAvatar: LiveData<Result<Boolean>> = _isCompletedUploadingAvatar

    private var _downloadAvatarUri: MutableLiveData<Result<Uri?>> = MutableLiveData()
    val downloadAvatarUri: LiveData<Result<Uri?>> = _downloadAvatarUri

    private var _isCompletedUpdatingAvatarUri: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompletedUpdatingAvatarUri: LiveData<Result<Boolean>> = _isCompletedUpdatingAvatarUri

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetUser() {
        viewModelScope.launch {
            try {
                val currentUser = getUser()
                _currentUser.value = Result.success(currentUser)
            } catch (ex: Exception) {
                _currentUser.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun updateNick(nick: String) {
        viewModelScope.launch {
            try {
                val isCompleted = updateNickUseCase(nick)
                _isCompletedUpdatingNick.value = Result.success(isCompleted)
            } catch (ex: Exception) {
                _isCompletedUpdatingNick.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun updateNickInDb(nick: String, uid: String) {
        viewModelScope.launch {
            try {
                updateNickInDbUseCase(nick ,uid)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }

    fun uploadAvatarAndGetIsCompleted(uri: Uri) {
        viewModelScope.launch {
            try {
                val isCompleted = uploadAvatarAndGetIsCompletedUseCase(uri)
                _isCompletedUploadingAvatar.value = Result.success(isCompleted)
            } catch (ex: Exception) {
                _isCompletedUploadingAvatar.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onGetDownloadAvatarUri(uri: Uri) {
        viewModelScope.launch {
            try {
                val downloadAvatarUri = getDownloadAvatarUriUseCase(uri)
                _downloadAvatarUri.value = Result.success(downloadAvatarUri)
            } catch (ex: Exception) {
                _downloadAvatarUri.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun updateAvatarUriAndGetIsCompleted(uri: Uri) {
        viewModelScope.launch {
            try {
                val isCompleted = updateAvatarAndGetIsCompletedUseCase(uri)
                _isCompletedUpdatingAvatarUri.value = Result.success(isCompleted)
            } catch (ex: Exception) {
                _isCompletedUpdatingAvatarUri.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun updateAvatarUriInDb(uid: String, uri: Uri) {
        viewModelScope.launch {
            try {
                updateAvatarUriInDbUseCase(uid, uri)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
