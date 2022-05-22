package com.example.jf.features.other.domain.useCases

import com.example.jf.features.other.data.repoImpl.UserRepoInOtherImpl
import com.example.jf.features.other.domain.repo.UserRepoInOther
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repo: UserRepoInOther
) {
    suspend operator fun invoke() {
        return withContext(Dispatchers.Main) {
            repo.signOut()
        }
    }
}
