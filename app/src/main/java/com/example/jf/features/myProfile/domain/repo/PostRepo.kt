package com.example.jf.features.myProfile.domain.repo

import com.example.jf.features.myProfile.domain.model.PostInList


interface PostRepo {
    suspend fun getPosts(postLimit: Int, uid: String): MutableList<PostInList?>

    suspend fun deletePost(postId: String, userId: String)
}
