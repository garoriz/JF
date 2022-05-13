package com.example.jf.features.myProfile.data.repoImpl

import com.example.jf.FirebaseAuthService
import com.example.jf.features.myProfile.data.mappers.UserMapper
import com.example.jf.features.myProfile.domain.model.User
import com.example.jf.features.myProfile.domain.repo.UserRepo


class UserRepoImpl : UserRepo {
    private val mapper = UserMapper()

    override suspend fun getCurrentUser(): User? {
        return mapper.map(FirebaseAuthService.getCurrentUser())
    }
}
