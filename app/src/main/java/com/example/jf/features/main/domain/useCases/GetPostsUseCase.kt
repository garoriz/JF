package com.example.jf.features.main.domain.useCases

import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.domain.repo.PostListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repo: PostListRepo
) {
    suspend operator fun invoke(postLimit: Int): MutableList<PostInList?> {
        return withContext(Dispatchers.Main) {
            repo.getPosts(postLimit)
        }
    }
}
