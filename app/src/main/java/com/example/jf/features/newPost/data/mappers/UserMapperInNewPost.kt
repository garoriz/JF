package com.example.jf.features.newPost.data.mappers

import com.example.jf.features.newPost.domain.model.User
import com.google.firebase.auth.FirebaseUser

class UserMapperInNewPost {
    fun map(currentUser: FirebaseUser?): User? {
        return if (currentUser != null) {
            User(currentUser.uid)
        } else
            null
    }
}
