package com.example.jf.features.main.data.repoImpl

import com.example.jf.features.main.data.mappers.UserMapperInMainFragment
import com.example.jf.features.main.domain.model.User
import com.example.jf.features.main.domain.repo.UserRepoInMain
import com.example.jf.firebase.FirebaseAuthService
import javax.inject.Inject

class UserRepoInMainImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val mapper: UserMapperInMainFragment
): UserRepoInMain {

    override suspend fun getCurrentUser(): User? {
        return mapper.map(firebaseAuthService.getCurrentUser())
    }
}
