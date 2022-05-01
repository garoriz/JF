package com.example.jf.features.registration.domain

import android.net.Uri
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val nick: String? = null,
    val urlPhoto: String? = null,
)
