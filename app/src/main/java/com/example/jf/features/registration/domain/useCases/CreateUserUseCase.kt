package com.example.jf.features.registration.domain.useCases

import com.example.jf.features.registration.data.repoImpl.RegistrationUserRepoImpl
import com.example.jf.features.registration.domain.repo.RegistrationUserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repo: RegistrationUserRepo
) {

    suspend operator fun invoke(email: String, password: String, nick: String): String? {
        return withContext(Dispatchers.Main) {
            repo.createUser(email, password, nick)
        }
    }
}
