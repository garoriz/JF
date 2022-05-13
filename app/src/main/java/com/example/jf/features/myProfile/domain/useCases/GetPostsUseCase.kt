package com.example.jf.features.myProfile.domain.useCases

import com.example.jf.features.myProfile.data.repoImpl.PostRepoImpl
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.domain.repo.PostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPostsUseCase {
    private val postRepo: PostRepo = PostRepoImpl()

    suspend operator fun invoke(postLimit: Int, uid: String): MutableList<PostInList?> {
        return withContext(Dispatchers.Main) {
            postRepo.getPosts(postLimit, uid)
        }
    }
}
