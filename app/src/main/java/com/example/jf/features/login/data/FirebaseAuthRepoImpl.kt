package com.example.jf.features.login.data

import com.example.jf.FirebaseAuthService
import com.example.jf.features.login.domain.repo.FirebaseAuthRepo

class FirebaseAuthRepoImpl : FirebaseAuthRepo {
    override suspend fun signInAndGetIsCompleted(email: String, password: String): Boolean {
        return FirebaseAuthService.signInAndGetIsCompleted(email, password)
    }
}
