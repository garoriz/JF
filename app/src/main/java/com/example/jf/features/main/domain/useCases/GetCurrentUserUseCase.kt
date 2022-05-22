package com.example.jf.features.main.domain.useCases

import com.example.jf.features.main.domain.model.User
import com.example.jf.features.main.domain.repo.UserRepoInMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repo: UserRepoInMain
) {
    suspend operator fun invoke(): User? {
        return withContext(Dispatchers.Main) {
            repo.getCurrentUser()
        }
    }
}
