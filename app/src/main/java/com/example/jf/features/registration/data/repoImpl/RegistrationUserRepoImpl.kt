package com.example.jf.features.registration.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.registration.domain.repo.RegistrationUserRepo
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class RegistrationUserRepoImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val firebaseAuthService: FirebaseAuthService,
): RegistrationUserRepo {
    override suspend fun createUser(email: String, password: String, nick: String): String? {
        return firebaseAuthService.createUserAndGetUid(email, password, nick)
    }

    override suspend fun addUserInDb(nick: String, uid: String) {
        dbRefService.addUser(nick, uid)
    }
}
