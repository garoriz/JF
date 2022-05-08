package com.example.jf.features.messages.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chat(
    val userId: String? = null,
    val otherUserId: String? = null,
    val isUnread: Boolean? = null,
)
