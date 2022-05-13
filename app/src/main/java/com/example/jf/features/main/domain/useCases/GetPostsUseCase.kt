package com.example.jf.features.main.domain.useCases

import com.example.jf.features.main.data.repoImpl.PostRepoImpl
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.domain.repo.PostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPostsUseCase {
    private val postRepo: PostRepo = PostRepoImpl()

    suspend operator fun invoke(postLimit: Int): MutableList<PostInList?> {
        return withContext(Dispatchers.Main) {
            postRepo.getPosts(postLimit)
        }
    }
}
