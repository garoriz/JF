package com.example.jf.features.main.domain.model

import com.example.jf.features.newPost.domain.model.Post
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PostInList(
    val id: String? = null,
    val userId: String? = null,
    val text: String? = null,
    val uriPhoto: String? = null,
    val uriVideo: String? = null,
)
