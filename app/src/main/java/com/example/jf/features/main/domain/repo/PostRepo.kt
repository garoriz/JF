package com.example.jf.features.main.domain.repo

import com.example.jf.features.main.domain.model.PostInList
import com.google.firebase.database.DataSnapshot

interface PostRepo {
    suspend fun getPosts(postLimit: Int): MutableList<PostInList?>
}
