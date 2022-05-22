package com.example.jf.features.newPost.domain.repo

import com.example.jf.features.newPost.domain.model.User

interface UserRepoInNewPost {
    suspend fun getCurrentUser(): User?
}
