package com.example.jf.features.editProfile.data.mappers

import com.example.jf.features.editProfile.domain.models.User
import com.google.firebase.auth.FirebaseUser

class UserMapper {
    fun map(currentUser: FirebaseUser?): User? {
        return if (currentUser != null) {
            User(currentUser.uid, currentUser.displayName, currentUser.photoUrl)
        } else
            null
    }
}
