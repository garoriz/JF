package com.example.jf.features.myProfile.data.repoImpl

import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.myProfile.data.mappers.PostMapperInMyProfile
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.domain.repo.PostRepoInMyProfile
import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import javax.inject.Inject


class PostRepoInMyProfileImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val firebaseAuthService: FirebaseAuthService,
    private val storageRefService: StorageRefService,
    private val mapper: PostMapperInMyProfile
): PostRepoInMyProfile {

    override suspend fun getPosts(postLimit: Int, uid: String): MutableList<PostInList?> {
        return mapper.map(dbRefService.updatePostsOfOneUser(postLimit, uid))
    }

    override suspend fun deletePost(postId: String, userId: String) {
        dbRefService.deletePost(postId, userId)
    }
}
