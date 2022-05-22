package com.example.jf.features.editProfile.domain.usecases

import com.example.jf.features.editProfile.domain.repositories.UserRepoInEditProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateNickUseCase @Inject constructor(
    private val repo: UserRepoInEditProfile
) {
    suspend operator fun invoke(nick: String): Boolean {
        return withContext(Dispatchers.Main) {
            repo.updateNick(nick)
        }
    }
}
