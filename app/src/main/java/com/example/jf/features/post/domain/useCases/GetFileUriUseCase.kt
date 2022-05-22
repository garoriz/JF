package com.example.jf.features.post.domain.useCases

import android.net.Uri
import com.example.jf.features.newPost.domain.model.Post
import com.example.jf.features.post.data.repoImpl.PostRepoImpl
import com.example.jf.features.post.domain.repo.PostRepo
import com.example.jf.features.post.domain.repo.UserRepoInPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFileUriUseCase @Inject constructor(
    private val repo: PostRepo
) {
    suspend operator fun invoke(uri: String): Uri? {
        return withContext(Dispatchers.Main) {
            repo.getFile(uri)
        }
    }
}
