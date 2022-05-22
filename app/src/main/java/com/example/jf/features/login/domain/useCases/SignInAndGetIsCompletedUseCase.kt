package com.example.jf.features.login.domain.useCases

import com.example.jf.features.login.domain.repo.FirebaseAuthRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInAndGetIsCompletedUseCase @Inject constructor(
    private val firebaseAuthRepo: FirebaseAuthRepo
) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return withContext(Dispatchers.Main) {
            firebaseAuthRepo.signInAndGetIsCompleted(email, password)
        }
    }
}
