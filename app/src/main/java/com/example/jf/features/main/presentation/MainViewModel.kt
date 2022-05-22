package com.example.jf.features.main.presentation

import androidx.lifecycle.*
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.domain.model.User
import com.example.jf.features.main.domain.useCases.GetCurrentUserUseCase
import com.example.jf.features.main.domain.useCases.GetPostsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getPostsUseCase: GetPostsUseCase,
): ViewModel() {
    private var _currentUser: MutableLiveData<Result<User?>> = MutableLiveData()
    val currentUser: LiveData<Result<User?>> = _currentUser

    private var _posts: MutableLiveData<Result<MutableList<PostInList?>?>?> = MutableLiveData()
    val posts: MutableLiveData<Result<MutableList<PostInList?>?>?> = _posts

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

    fun onGetPosts(postLimit: Int) {
        viewModelScope.launch {
            try {
                val posts = getPostsUseCase(postLimit)
                _posts.value = Result.success(posts)
            } catch (ex: Exception) {
                _posts.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun clearPostsLiveData() {
        _posts.value = null
    }
}
