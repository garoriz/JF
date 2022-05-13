package com.example.jf.features.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.DbRefService
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.domain.model.User
import com.example.jf.features.main.domain.useCases.GetCurrentUserUseCase
import com.example.jf.features.main.domain.useCases.GetPostsUseCase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val getCurrentUserUseCase = GetCurrentUserUseCase()
    val getPostsUseCase = GetPostsUseCase()

    private var _currentUser: MutableLiveData<Result<User?>> = MutableLiveData()
    val currentUser: LiveData<Result<User?>> = _currentUser

    private var _posts: MutableLiveData<Result<MutableList<PostInList?>?>> = MutableLiveData()
    val posts: LiveData<Result<MutableList<PostInList?>?>> = _posts

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
}
