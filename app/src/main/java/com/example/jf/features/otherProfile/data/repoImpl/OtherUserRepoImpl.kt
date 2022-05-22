package com.example.jf.features.otherProfile.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.otherProfile.domain.model.User
import com.example.jf.features.otherProfile.domain.repo.OtherUserRepo
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class OtherUserRepoImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val firebaseAuthService: FirebaseAuthService,
): OtherUserRepo {

    override suspend fun getUserInfo(uid: String): User? {
        return dbRefService.getUser(uid)
    }
}
