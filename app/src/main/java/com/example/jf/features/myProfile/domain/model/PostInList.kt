package com.example.jf.features.myProfile.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PostInList (
    @PrimaryKey
    val id: String? = null,
    val userId: String? = null,
    val heading: String? = null,
    val text: String? = null,
    val uriPhoto: String? = null,
    val uriVideo: String? = null,
)
