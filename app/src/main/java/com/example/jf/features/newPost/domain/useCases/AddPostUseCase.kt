package com.example.jf.features.newPost.domain.useCases

import com.example.jf.features.myProfile.domain.repo.UserRepo
import com.example.jf.features.newPost.data.repoImpl.NewPostRepoImpl
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.newPost.domain.repo.NewPostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPostUseCase @Inject constructor(
    private val repo: NewPostRepo
) {
    suspend operator fun invoke(post: Post, uid: String) {
        return withContext(Dispatchers.Main) {
            repo.addPost(post, uid)
        }
    }
}
