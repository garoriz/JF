package com.example.jf.features.post.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.otherProfile.domain.model.User
import com.example.jf.features.post.data.mappers.UserMapperInPost
import com.example.jf.features.post.domain.model.CurrentUser
import com.example.jf.features.post.domain.repo.UserRepoInPost
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class UserRepoInPostImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val firebaseAuthService: FirebaseAuthService,
    private val mapper: UserMapperInPost
): UserRepoInPost {

    override suspend fun getCurrentUser(): CurrentUser? {
        return mapper.map(firebaseAuthService.getCurrentUser())
    }

    override suspend fun getAuthor(userId: String): User? {
        return dbRefService.getUser(userId)
    }
}
