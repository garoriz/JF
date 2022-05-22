package com.example.jf.features.otherProfile.domain.repo

import com.example.jf.features.main.domain.model.PostInList


interface UserPostRepo {
    suspend fun getPosts(postLimit: Int, uid: String): MutableList<PostInList?>
}
