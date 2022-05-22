package com.example.jf.features.otherProfile.domain.useCases

import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.otherProfile.data.repoImpl.UserPostRepoImpl
import com.example.jf.features.otherProfile.domain.repo.OtherUserRepo
import com.example.jf.features.otherProfile.domain.repo.UserPostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repo: UserPostRepo
) {
    suspend operator fun invoke(postLimit: Int, uid: String): MutableList<PostInList?> {
        return withContext(Dispatchers.Main) {
            repo.getPosts(postLimit, uid)
        }
    }
}
