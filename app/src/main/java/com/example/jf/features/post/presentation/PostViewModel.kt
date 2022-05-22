package com.example.jf.features.post.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.otherProfile.domain.model.User
import com.example.jf.features.post.domain.model.CurrentUser
import com.example.jf.features.post.domain.useCases.GetAuthorUseCase
import com.example.jf.features.post.domain.useCases.GetCurrentUserUseCase
import com.example.jf.features.post.domain.useCases.GetFileUriUseCase
import com.example.jf.features.post.domain.useCases.GetPostUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val getAuthorUseCase: GetAuthorUseCase,
    private val getFileUriUseCase: GetFileUriUseCase
) : ViewModel() {

    private var _currentUser: MutableLiveData<Result<CurrentUser?>> = MutableLiveData()
    val currentUser: LiveData<Result<CurrentUser?>> = _currentUser

    private var _post: MutableLiveData<Result<Post?>> = MutableLiveData()
    val post: LiveData<Result<Post?>> = _post

    private var _author: MutableLiveData<Result<User?>> = MutableLiveData()
    val author: LiveData<Result<User?>> = _author

    private var _photoUri: MutableLiveData<Result<Uri?>> = MutableLiveData()
    val photoUri: LiveData<Result<Uri?>> = _photoUri

    private var _videoUri: MutableLiveData<Result<Uri?>> = MutableLiveData()
    val videoUri: LiveData<Result<Uri?>> = _videoUri

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

    fun onGetPost(id: String) {
        viewModelScope.launch {
            try {
                val post = getPostUseCase(id)
                _post.value = Result.success(post)
            } catch (ex: Exception) {
                _post.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onGetAuthor(userId: String) {
        viewModelScope.launch {
            try {
                val author = getAuthorUseCase(userId)
                _author.value = Result.success(author)
            } catch (ex: Exception) {
                _author.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onGetPhotoUri(uri: String) {
        viewModelScope.launch {
            try {
                val photoUri = getFileUriUseCase(uri)
                _photoUri.value = Result.success(photoUri)
            } catch (ex: Exception) {
                _photoUri.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onGetVideoUri(uri: String) {
        viewModelScope.launch {
            try {
                val videoUri = getFileUriUseCase(uri)
                _videoUri.value = Result.success(videoUri)
            } catch (ex: Exception) {
                _videoUri.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
