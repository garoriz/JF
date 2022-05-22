package com.example.jf.features.post.domain.repo

import com.example.jf.features.otherProfile.domain.model.User
import com.example.jf.features.post.domain.model.CurrentUser

interface UserRepoInPost {
    suspend fun getCurrentUser(): CurrentUser?

    suspend fun getAuthor(userId: String): User?
}
