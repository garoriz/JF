package com.example.jf.features.myProfile.domain.repo

import com.example.jf.features.myProfile.domain.model.User

interface UserRepo {
    suspend fun getCurrentUser(): User?
}
