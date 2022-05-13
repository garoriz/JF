package com.example.jf.features.login.domain.repo

import com.example.jf.features.editProfile.domain.models.User

interface FirebaseAuthRepo {
    suspend fun signInAndGetIsCompleted(email: String, password: String): Boolean
}
