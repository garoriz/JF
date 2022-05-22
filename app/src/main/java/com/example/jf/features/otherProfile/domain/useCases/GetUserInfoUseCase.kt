package com.example.jf.features.otherProfile.domain.useCases

import com.example.jf.features.otherProfile.data.repoImpl.OtherUserRepoImpl
import com.example.jf.features.otherProfile.domain.model.User
import com.example.jf.features.otherProfile.domain.repo.OtherUserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repo: OtherUserRepo
) {
    suspend operator fun invoke(uid: String): User? {
        return withContext(Dispatchers.Main) {
            repo.getUserInfo(uid)
        }
    }
}
