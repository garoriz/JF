package com.example.jf.features.registration.domain.useCases

import com.example.jf.features.post.domain.repo.UserRepoInPost
import com.example.jf.features.registration.data.repoImpl.RegistrationUserRepoImpl
import com.example.jf.features.registration.domain.repo.RegistrationUserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddUserInDbUseCase @Inject constructor(
    private val repo: RegistrationUserRepo
) {
    suspend operator fun invoke(nick: String, uid: String) {
        return withContext(Dispatchers.Main) {
            repo.addUserInDb(nick, uid)
        }
    }
}
