package com.example.jf.features.newPost.domain.useCases

import com.example.jf.features.myProfile.domain.repo.UserRepo
import com.example.jf.features.newPost.data.repoImpl.UserRepoInNewPostImpl
import com.example.jf.features.newPost.domain.model.User
import com.example.jf.features.newPost.domain.repo.UserRepoInNewPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repo: UserRepoInNewPost
) {
    suspend operator fun invoke(): User? {
        return withContext(Dispatchers.Main) {
            repo.getCurrentUser()
        }
    }
}
