package com.example.jf.features.main.domain.useCases

import com.example.jf.features.main.data.repoImpl.UserRepoImpl
import com.example.jf.features.main.domain.model.User
import com.example.jf.features.main.domain.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCurrentUserUseCase {
    private val userRepo: UserRepo = UserRepoImpl()

    suspend operator fun invoke(): User? {
        return withContext(Dispatchers.Main) {
            userRepo.getCurrentUser()
        }
    }
}
