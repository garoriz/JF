package com.example.jf.features.otherProfile.data.repoImpl


import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.otherProfile.data.mappers.UserPostMapper
import com.example.jf.features.otherProfile.domain.repo.UserPostRepo
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject

class UserPostRepoImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val mapper: UserPostMapper
): UserPostRepo {

    override suspend fun getPosts(postLimit: Int, uid: String): MutableList<PostInList?> {
        return mapper.map(dbRefService.updatePostsOfOneUser(postLimit, uid))
    }
}
