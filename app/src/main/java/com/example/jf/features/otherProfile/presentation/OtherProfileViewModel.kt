package com.example.jf.features.otherProfile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.otherProfile.domain.model.User
import com.example.jf.features.otherProfile.domain.useCases.GetPostsUseCase
import com.example.jf.features.otherProfile.domain.useCases.GetUserInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class OtherProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {
    private var _userInfo: MutableLiveData<Result<User?>> = MutableLiveData()
    val userInfo: LiveData<Result<User?>> = _userInfo

    private var _posts: MutableLiveData<Result<MutableList<PostInList?>?>?> = MutableLiveData()
    val posts: MutableLiveData<Result<MutableList<PostInList?>?>?> = _posts

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetUserInfo(uid: String) {
        viewModelScope.launch {
            try {
                val userInfo = getUserInfoUseCase(uid)
                _userInfo.value = Result.success(userInfo)
            } catch (ex: Exception) {
                _userInfo.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

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

    fun clearPostsLiveData() {
        _posts.value = null
    }
}
