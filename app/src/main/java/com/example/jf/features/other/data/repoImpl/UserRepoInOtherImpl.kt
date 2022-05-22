package com.example.jf.features.other.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.other.data.mappers.UserMapperInOther
import com.example.jf.features.other.domain.model.User
import com.example.jf.features.other.domain.repo.UserRepoInOther
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class UserRepoInOtherImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val mapper: UserMapperInOther
): UserRepoInOther {

    override suspend fun getCurrentUser(): User? {
        return mapper.map(firebaseAuthService.getCurrentUser())
    }

    override suspend fun signOut() {
        firebaseAuthService.signOut()
    }
}
