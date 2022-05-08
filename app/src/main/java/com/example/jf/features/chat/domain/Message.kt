package com.example.jf.features.chat.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    val id: String? = null,
    val userId: String? = null,
    val text: String? = null,
)
