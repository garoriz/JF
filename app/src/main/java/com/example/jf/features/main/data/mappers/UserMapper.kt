package com.example.jf.features.main.data.mappers

import com.example.jf.features.main.domain.model.User
import com.google.firebase.auth.FirebaseUser

class UserMapper {
    fun map(currentUser: FirebaseUser?): User? {
        return if (currentUser != null) {
            User(currentUser.photoUrl)
        } else
            null
    }
}
