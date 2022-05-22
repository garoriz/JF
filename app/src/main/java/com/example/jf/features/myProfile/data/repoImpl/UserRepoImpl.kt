package com.example.jf.features.myProfile.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.myProfile.data.mappers.UserMapper
import com.example.jf.features.myProfile.domain.model.User
import com.example.jf.features.myProfile.domain.repo.UserRepo
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject


class UserRepoImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val mapper: UserMapper
): UserRepo {

    override suspend fun getCurrentUser(): User? {
        return mapper.map(firebaseAuthService.getCurrentUser())
    }
}
