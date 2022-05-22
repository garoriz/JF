package com.example.jf.features.main.domain.repo

import com.example.jf.features.main.domain.model.PostInList

interface PostListRepo {
    suspend fun getPosts(postLimit: Int): MutableList<PostInList?>
}
