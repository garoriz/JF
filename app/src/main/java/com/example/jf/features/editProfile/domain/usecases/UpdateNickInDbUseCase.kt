package com.example.jf.features.editProfile.domain.usecases

import com.example.jf.features.editProfile.data.UserRepoImpl
import com.example.jf.features.editProfile.domain.repositories.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateNickInDbUseCase {
    private val userRepo: UserRepo = UserRepoImpl()

    suspend operator fun invoke(nick: String, uid: String) {
        return withContext(Dispatchers.Main) {
            userRepo.updateNickInDb(nick, uid)
        }
    }
}
