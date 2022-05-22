package com.example.jf.features.myProfile.domain.useCases

import com.example.jf.features.myProfile.domain.repo.PostRepoInMyProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepo: PostRepoInMyProfile
) {
    suspend operator fun invoke(postId: String, userId: String) {
        return withContext(Dispatchers.Main) {
            postRepo.deletePost(postId, userId)
        }
    }
}
