package com.example.jf.features.main.data.repoImpl

import com.example.jf.FirebaseAuthService
import com.example.jf.features.main.data.mappers.UserMapper
import com.example.jf.features.main.domain.model.User
import com.example.jf.features.main.domain.repo.UserRepo

class UserRepoImpl : UserRepo {
    private val mapper = UserMapper()

    override suspend fun getCurrentUser(): User? {
        return mapper.map(FirebaseAuthService.getCurrentUser())
    }
}
