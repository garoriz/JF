package com.example.jf.features.post.domain.useCases

import com.example.jf.features.post.data.repoImpl.UserRepoInPostImpl
import com.example.jf.features.post.domain.model.CurrentUser
import com.example.jf.features.post.domain.repo.UserRepoInPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repo: UserRepoInPost
) {
    suspend operator fun invoke(): CurrentUser? {
        return withContext(Dispatchers.Main) {
            repo.getCurrentUser()
        }
    }
}
