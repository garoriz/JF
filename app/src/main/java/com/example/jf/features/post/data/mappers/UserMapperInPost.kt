package com.example.jf.features.post.data.mappers

import com.example.jf.features.post.domain.model.CurrentUser
import com.google.firebase.auth.FirebaseUser

class UserMapperInPost {
    fun map(currentUser: FirebaseUser?): CurrentUser? {
        return if (currentUser != null) {
            CurrentUser(currentUser.uid)
        } else
            null
    }
}
