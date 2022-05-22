package com.example.jf.features.settings.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.settings.domain.repo.UserRepoInSettings
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class UserRepoInSettingsImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
): UserRepoInSettings {
    override suspend fun changePassword(password: String) {
        firebaseAuthService.changePassword(password)
    }
}
