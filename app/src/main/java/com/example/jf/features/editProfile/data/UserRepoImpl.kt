package com.example.jf.features.editProfile.data

import android.net.Uri
import com.example.jf.DbRefService
import com.example.jf.FirebaseAuthService
import com.example.jf.StorageRefService
import com.example.jf.features.editProfile.data.mappers.UserMapper
import com.example.jf.features.editProfile.domain.models.User
import com.example.jf.features.editProfile.domain.repositories.UserRepo

class UserRepoImpl : UserRepo {
    private val mapper = UserMapper()

    override suspend fun getCurrentUser(): User? {
        return mapper.map(FirebaseAuthService.getCurrentUser())
    }

    override suspend fun updateNick(nick: String): Boolean {
        return FirebaseAuthService.updateNick(nick)
    }

    override suspend fun updateNickInDb(nick: String, uid: String) {
        DbRefService.updateNick(nick, uid)
    }

    override suspend fun uploadAvatarAndIsCompleted(uri: Uri): Boolean {
        return StorageRefService.uploadAvatarAndIsCompleted(uri)
    }

    override suspend fun getDownloadAvatarUri(uri: Uri): Uri? {
        return StorageRefService.getDownloadAvatarUri(uri)
    }

    override suspend fun updateAvatarUriAndGetIsCompleted(uri: Uri): Boolean {
        return FirebaseAuthService.updateAvatarUriAndGetIsCompleted(uri)
    }

    override suspend fun updateAvatarUriInDb(uid: String, uri: Uri) {
        DbRefService.updateAvatarUri(uid, uri)
    }
}
