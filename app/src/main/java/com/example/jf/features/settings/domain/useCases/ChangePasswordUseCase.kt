package com.example.jf.features.settings.domain.useCases

import com.example.jf.features.registration.domain.repo.RegistrationUserRepo
import com.example.jf.features.settings.data.repoImpl.UserRepoInSettingsImpl
import com.example.jf.features.settings.domain.repo.UserRepoInSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repo: UserRepoInSettings
) {

    suspend operator fun invoke(password: String) {
        return withContext(Dispatchers.Main) {
            repo.changePassword(password)
        }
    }
}
