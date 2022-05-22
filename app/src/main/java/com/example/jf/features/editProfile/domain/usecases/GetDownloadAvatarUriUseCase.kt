package com.example.jf.features.editProfile.domain.usecases

import android.net.Uri
import com.example.jf.features.editProfile.domain.repositories.UserRepoInEditProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDownloadAvatarUriUseCase @Inject constructor(
    private val repo: UserRepoInEditProfile
) {

    suspend operator fun invoke(uri: Uri): Uri? {
        return withContext(Dispatchers.Main) {
            repo.getDownloadAvatarUri(uri)
        }
    }
}
