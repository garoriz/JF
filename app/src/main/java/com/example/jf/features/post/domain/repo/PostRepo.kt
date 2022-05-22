package com.example.jf.features.post.domain.repo

import android.net.Uri
import com.example.jf.features.newPost.domain.model.Post

interface PostRepo {
    suspend fun getPost(id: String): Post?

    suspend fun getFile(uri: String): Uri?
}
