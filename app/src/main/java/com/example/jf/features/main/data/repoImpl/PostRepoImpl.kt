package com.example.jf.features.main.data.repoImpl

import com.example.jf.DbRefService
import com.example.jf.features.main.data.mappers.PostMapper
import com.example.jf.features.main.domain.model.PostInList
import com.example.jf.features.main.domain.repo.PostRepo

class PostRepoImpl : PostRepo {
    private val mapper = PostMapper()

    override suspend fun getPosts(postLimit: Int): MutableList<PostInList?> {
        return mapper.map(DbRefService.updatePosts(postLimit))
    }
}
