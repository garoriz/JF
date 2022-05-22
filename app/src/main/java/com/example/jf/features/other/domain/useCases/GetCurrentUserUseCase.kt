package com.example.jf.features.other.domain.useCases

import com.example.jf.features.myProfile.domain.repo.UserRepo
import com.example.jf.features.other.data.repoImpl.UserRepoInOtherImpl
import com.example.jf.features.other.domain.model.User
import com.example.jf.features.other.domain.repo.UserRepoInOther
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repo: UserRepoInOther
) {
    suspend operator fun invoke(): User? {
        return withContext(Dispatchers.Main) {
            repo.getCurrentUser()
        }
    }
}
