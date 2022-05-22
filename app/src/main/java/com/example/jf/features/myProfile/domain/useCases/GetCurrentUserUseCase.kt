package com.example.jf.features.myProfile.domain.useCases

import com.example.jf.features.myProfile.data.repoImpl.UserRepoImpl
import com.example.jf.features.myProfile.domain.model.User
import com.example.jf.features.myProfile.domain.repo.PostRepoInMyProfile
import com.example.jf.features.myProfile.domain.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repo: UserRepo
) {
    suspend operator fun invoke(): User? {
        return withContext(Dispatchers.Main) {
            repo.getCurrentUser()
        }
    }
}
