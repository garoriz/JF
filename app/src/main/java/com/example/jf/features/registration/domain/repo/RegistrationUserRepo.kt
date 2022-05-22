package com.example.jf.features.registration.domain.repo

import com.example.jf.features.post.domain.model.CurrentUser
import com.example.jf.features.registration.domain.model.User

interface RegistrationUserRepo {
    suspend fun createUser(email: String, password: String, nick: String): String?

    suspend fun addUserInDb(nick: String, uid: String)
}
