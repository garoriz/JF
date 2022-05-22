package com.example.jf.features.newPost.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.newPost.data.mappers.UserMapperInNewPost
import com.example.jf.features.newPost.domain.model.User
import com.example.jf.features.newPost.domain.repo.UserRepoInNewPost
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class UserRepoInNewPostImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val mapper: UserMapperInNewPost
): UserRepoInNewPost {

    override suspend fun getCurrentUser(): User? {
        return mapper.map(firebaseAuthService.getCurrentUser())
    }
}
