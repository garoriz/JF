package com.example.jf.features.editProfile.domain.usecases

import android.net.Uri
import com.example.jf.features.editProfile.data.UserRepoInEditProfileImpl
import com.example.jf.features.editProfile.domain.repositories.UserRepoInEditProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateAvatarUriInDbUseCase @Inject constructor(
    private val repo: UserRepoInEditProfile
) {
    suspend operator fun invoke(uid: String, uri: Uri) {
        return withContext(Dispatchers.Main) {
            repo.updateAvatarUriInDb(uid, uri)
        }
    }
}
