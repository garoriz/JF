package com.example.jf.features.newPost.domain.repo

import android.net.Uri
import com.example.jf.features.myProfile.domain.model.PostInList
import com.example.jf.features.newPost.domain.model.Post

interface NewPostRepo {
    suspend fun addPost(post: Post, uid: String)

    suspend fun uploadFileAnfGetIsCompleted(uri: Uri, type: String): Boolean
}
