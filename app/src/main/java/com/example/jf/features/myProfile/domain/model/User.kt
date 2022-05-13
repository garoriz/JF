package com.example.jf.features.myProfile.domain.model

import android.net.Uri

data class User(
    val uid: String?,
    val nick: String?,
    val photoUrl: Uri?,
)
