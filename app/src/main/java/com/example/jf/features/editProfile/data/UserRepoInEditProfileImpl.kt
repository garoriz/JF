package com.example.jf.features.editProfile.data

import android.net.Uri

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.editProfile.domain.models.User
import com.example.jf.features.editProfile.domain.repositories.UserRepoInEditProfile
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class UserRepoInEditProfileImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val firebaseAuthService: FirebaseAuthService,
    private val storageRefService: StorageRefService,
    private val mapper: UserMapperInEditProfile
) : UserRepoInEditProfile {

    override suspend fun getCurrentUser(): User? {
        return mapper.map(firebaseAuthService.getCurrentUser())
    }

    override suspend fun updateNick(nick: String): Boolean {
        return firebaseAuthService.updateNick(nick)
    }

    override suspend fun updateNickInDb(nick: String, uid: String) {
        dbRefService.updateNick(nick, uid)
    }

    override suspend fun uploadAvatarAndIsCompleted(uri: Uri): Boolean {
        return storageRefService.uploadAvatarAndIsCompleted(uri)
    }

    override suspend fun getDownloadAvatarUri(uri: Uri): Uri? {
        return storageRefService.getDownloadAvatarUri(uri)
    }

    override suspend fun updateAvatarUriAndGetIsCompleted(uri: Uri): Boolean {
        return firebaseAuthService.updateAvatarUriAndGetIsCompleted(uri)
    }

    override suspend fun updateAvatarUriInDb(uid: String, uri: Uri) {
        dbRefService.updateAvatarUri(uid, uri)
    }
}
