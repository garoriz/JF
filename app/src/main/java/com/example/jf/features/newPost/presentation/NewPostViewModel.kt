package com.example.jf.features.newPost.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.newPost.domain.model.User
import com.example.jf.features.newPost.domain.useCases.AddPostUseCase
import com.example.jf.features.newPost.domain.useCases.GetCurrentUserUseCase
import com.example.jf.features.newPost.domain.useCases.UploadFileAndGetIsCompletedUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewPostViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val uploadFileAndGetIsCompletedUseCase: UploadFileAndGetIsCompletedUseCase,
): ViewModel() {
    private var _currentUser: MutableLiveData<Result<User?>> = MutableLiveData()
    val currentUser: LiveData<Result<User?>> = _currentUser

    private var _isCompleted: MutableLiveData<Result<Boolean>> = MutableLiveData()
    val isCompleted: LiveData<Result<Boolean>> = _isCompleted

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

    fun addPost(post: Post, uid: String) {
        viewModelScope.launch {
            try {
                addPostUseCase(post, uid)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }

    fun uploadFileAndGetIsCompleted(uri: Uri, type: String) {
        viewModelScope.launch {
            try {
                val isCompleted = uploadFileAndGetIsCompletedUseCase(uri, type)
                _isCompleted.value = Result.success(isCompleted)
            } catch (ex: Exception) {
                _isCompleted.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
