package com.example.jf.features.editProfile.domain.usecases

import com.example.jf.features.editProfile.domain.models.User
import com.example.jf.features.editProfile.domain.repositories.UserRepoInEditProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repo: UserRepoInEditProfile
) {
    suspend operator fun invoke(): User? {
        return withContext(Dispatchers.Main) {
            repo.getCurrentUser()
        }
    }
}
