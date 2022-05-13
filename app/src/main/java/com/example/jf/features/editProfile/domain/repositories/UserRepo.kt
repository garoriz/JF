package com.example.jf.features.editProfile.domain.repositories

import android.net.Uri
import com.example.jf.features.editProfile.domain.models.User

interface UserRepo {
    suspend fun getCurrentUser(): User?

    suspend fun updateNick(nick: String): Boolean

    suspend fun updateNickInDb(nick: String, uid: String)

    suspend fun uploadAvatarAndIsCompleted(uri: Uri): Boolean

    suspend fun getDownloadAvatarUri(uri: Uri): Uri?

    suspend fun updateAvatarUriAndGetIsCompleted(uri: Uri): Boolean

    suspend fun updateAvatarUriInDb(uid: String, uri: Uri)
}
