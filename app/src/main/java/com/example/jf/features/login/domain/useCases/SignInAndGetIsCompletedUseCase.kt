package com.example.jf.features.login.domain.useCases

import com.example.jf.features.login.data.FirebaseAuthRepoImpl
import com.example.jf.features.login.domain.repo.FirebaseAuthRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInAndGetIsCompletedUseCase {
    private val firebaseAuthRepo: FirebaseAuthRepo = FirebaseAuthRepoImpl()

    suspend operator fun invoke(email: String, password: String): Boolean {
        return withContext(Dispatchers.Main) {
            firebaseAuthRepo.signInAndGetIsCompleted(email, password)
        }
    }
}
