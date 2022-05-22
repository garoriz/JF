package com.example.jf.features.main.data.repoImpl

import com.example.jf.features.main.data.mappers.PostMapper
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.domain.repo.PostListRepo
import com.example.jf.firebase.DbRefService
import javax.inject.Inject

class PostListRepoImpl @Inject constructor(
    private val dbRefService: DbRefService,
    private val mapper: PostMapper
): PostListRepo {
    override suspend fun getPosts(postLimit: Int): MutableList<PostInList?> {
        return mapper.map(dbRefService.updatePosts(postLimit))
    }
}
