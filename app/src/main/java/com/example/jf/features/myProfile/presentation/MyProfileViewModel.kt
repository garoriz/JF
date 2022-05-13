package com.example.jf.features.myProfile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.domain.model.User
import com.example.jf.features.myProfile.domain.useCases.DeletePostUseCase
import com.example.jf.features.myProfile.domain.useCases.GetCurrentUserUseCase
import com.example.jf.features.myProfile.domain.useCases.GetPostsUseCase
import kotlinx.coroutines.launch

class MyProfileViewModel : ViewModel() {
    val getPostsUseCase = GetPostsUseCase()
    val getCurrentUserUseCase = GetCurrentUserUseCase()
    val deletePostUseCase = DeletePostUseCase()

    private var _posts: MutableLiveData<Result<MutableList<PostInList?>?>> = MutableLiveData()
    val posts: LiveData<Result<MutableList<PostInList?>?>> = _posts

    private var _currentUser: MutableLiveData<Result<User?>> = MutableLiveData()
    val currentUser: LiveData<Result<User?>> = _currentUser

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetPosts(postLimit: Int, uid: String) {
        viewModelScope.launch {
            try {
                val posts = getPostsUseCase(postLimit, uid)
                _posts.value = Result.success(posts)
            } catch (ex: Exception) {
                _posts.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

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

    fun deletePost(postId: String, userId: String) {
        viewModelScope.launch {
            try {
                deletePostUseCase(postId, userId)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
