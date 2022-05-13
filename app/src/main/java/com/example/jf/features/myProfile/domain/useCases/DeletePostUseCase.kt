package com.example.jf.features.myProfile.domain.useCases

import com.example.jf.features.myProfile.data.repoImpl.PostRepoImpl
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.domain.repo.PostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeletePostUseCase {
    private val postRepo: PostRepo = PostRepoImpl()

    suspend operator fun invoke(postId: String, userId: String) {
        return withContext(Dispatchers.Main) {
            postRepo.deletePost(postId, userId)
        }
    }
}
