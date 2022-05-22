package com.example.jf.features.post.domain.useCases

import com.example.jf.features.otherProfile.domain.model.User
import com.example.jf.features.otherProfile.domain.repo.OtherUserRepo
import com.example.jf.features.post.data.repoImpl.UserRepoInPostImpl
import com.example.jf.features.post.domain.repo.UserRepoInPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAuthorUseCase @Inject constructor(
    private val repo: UserRepoInPost
) {
    suspend operator fun invoke(userId: String): User? {
        return withContext(Dispatchers.Main) {
            repo.getAuthor(userId)
        }
    }
}
