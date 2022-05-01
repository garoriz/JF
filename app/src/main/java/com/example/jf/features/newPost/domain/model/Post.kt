package com.example.jf.features.newPost.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    val userId: String? = null,
    val text: String? = null,
    val urisPhoto: List<String>? = null,
    val urisVideo: List<String>? = null,
)
