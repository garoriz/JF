package com.example.jf.features.settings.domain.repo

interface UserRepoInSettings {
    suspend fun changePassword(password: String)
}
