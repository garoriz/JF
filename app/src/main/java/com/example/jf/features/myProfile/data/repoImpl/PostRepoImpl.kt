package com.example.jf.features.myProfile.data.repoImpl

import com.example.jf.DbRefService
import com.example.jf.features.myProfile.data.mappers.PostMapper
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.myProfile.domain.repo.PostRepo


class PostRepoImpl : PostRepo {
    private val mapper = PostMapper()

    override suspend fun getPosts(postLimit: Int, uid: String): MutableList<PostInList?> {
        return mapper.map(DbRefService.updateOneUser(postLimit, uid))
    }

    override suspend fun deletePost(postId: String, userId: String) {
        DbRefService.deletePost(postId, userId)
    }
}
