package com.example.jf.features.other.data.mappers

import com.example.jf.features.other.domain.model.User
import com.google.firebase.auth.FirebaseUser

class UserMapperInOther {
    fun map(currentUser: FirebaseUser?): User? {
        return if (currentUser != null) {
            User(currentUser.uid)
        } else
            null
    }
}
