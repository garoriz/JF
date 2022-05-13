package com.example.jf.features.editProfile.domain.models

import android.net.Uri

data class User(
    val uid: String?,
    val nick: String?,
    val photoUrl: Uri?,
)
