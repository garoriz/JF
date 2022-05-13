package com.example.jf.features.editProfile.domain.usecases

import android.net.Uri
import com.example.jf.features.editProfile.data.UserRepoImpl
import com.example.jf.features.editProfile.domain.repositories.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDownloadAvatarUriUseCase {
    private val userRepo: UserRepo = UserRepoImpl()

    suspend operator fun invoke(uri: Uri): Uri? {
        return withContext(Dispatchers.Main) {
            userRepo.getDownloadAvatarUri(uri)
        }
    }
}
