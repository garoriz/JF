package com.example.jf.features.main.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PostInList(
    val id: String? = null,
    val userId: String? = null,
    val heading: String? = null,
    val text: String? = null,
    val uriPhoto: String? = null,
    val uriVideo: String? = null,
)
