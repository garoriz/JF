package com.example.jf.features.myProfile.domain.useCases

import com.example.jf.features.myProfile.data.repoImpl.PostRepoInMyProfileImpl
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.domain.repo.PostRepoInMyProfile
import com.example.jf.features.myProfile.domain.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repo: PostRepoInMyProfile
) {
    suspend operator fun invoke(postLimit: Int, uid: String): MutableList<PostInList?> {
        return withContext(Dispatchers.Main) {
            repo.getPosts(postLimit, uid)
        }
    }
}
