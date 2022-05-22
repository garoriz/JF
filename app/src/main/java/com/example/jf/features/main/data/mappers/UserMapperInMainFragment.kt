package com.example.jf.features.main.data.mappers

import com.example.jf.features.main.domain.model.User
import com.google.firebase.auth.FirebaseUser

class UserMapperInMainFragment {
    fun map(currentUser: FirebaseUser?): User? {
        return if (currentUser != null) {
            User(currentUser.uid, currentUser.photoUrl)
        } else
            null
    }
}
