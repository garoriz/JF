package com.example.jf.features.editProfile.domain.usecases

import com.example.jf.FirebaseAuthService
import com.example.jf.features.editProfile.data.UserRepoImpl
import com.example.jf.features.editProfile.data.mappers.UserMapper
import com.example.jf.features.editProfile.domain.models.User
import com.example.jf.features.editProfile.domain.repositories.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserUseCase {
    private val userRepo: UserRepo = UserRepoImpl()

    suspend operator fun invoke(): User? {
        return withContext(Dispatchers.Main) {
            userRepo.getCurrentUser()
        }
    }
}
