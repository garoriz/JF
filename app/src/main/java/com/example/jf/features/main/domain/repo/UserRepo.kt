package com.example.jf.features.main.domain.repo

import com.example.jf.features.main.domain.model.User


interface UserRepo {
    suspend fun getCurrentUser(): User?
}
