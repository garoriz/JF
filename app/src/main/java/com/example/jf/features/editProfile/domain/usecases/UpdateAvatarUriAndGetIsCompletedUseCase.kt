package com.example.jf.features.editProfile.domain.usecases

import android.net.Uri
import com.example.jf.features.editProfile.data.UserRepoImpl
import com.example.jf.features.editProfile.domain.repositories.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateAvatarUriAndGetIsCompletedUseCase {
    private val userRepo: UserRepo = UserRepoImpl()

    suspend operator fun invoke(uri: Uri): Boolean {
        return withContext(Dispatchers.Main) {
            userRepo.updateAvatarUriAndGetIsCompleted(uri)
        }
    }
}
