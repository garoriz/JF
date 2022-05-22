package com.example.jf.features.otherProfile.domain.repo

import com.example.jf.features.otherProfile.domain.model.User
import com.google.firebase.auth.FirebaseUser


interface OtherUserRepo {
    suspend fun getUserInfo(uid: String): User?
}
