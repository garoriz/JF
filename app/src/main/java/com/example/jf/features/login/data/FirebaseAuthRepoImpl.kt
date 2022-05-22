package com.example.jf.features.login.data

import com.example.jf.features.login.domain.repo.FirebaseAuthRepo
import com.example.jf.firebase.FirebaseAuthService
import javax.inject.Inject

class FirebaseAuthRepoImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) : FirebaseAuthRepo {
    override suspend fun signInAndGetIsCompleted(email: String, password: String): Boolean {
        return firebaseAuthService.signInAndGetIsCompleted(email, password)
    }
}
