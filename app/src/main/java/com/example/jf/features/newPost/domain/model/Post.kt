package com.example.jf.features.newPost.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    val author: String? = null,
    val text: String? = null,
    val urlsPhoto: List<String>? = null,
    val urlsVideo: List<String>? = null,
    val urlsMusic: List<String>? = null,
)
