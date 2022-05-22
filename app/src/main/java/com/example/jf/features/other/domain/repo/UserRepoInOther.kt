package com.example.jf.features.other.domain.repo

import com.example.jf.features.other.domain.model.User


interface UserRepoInOther {
    suspend fun getCurrentUser(): User?

    suspend fun signOut()
}
