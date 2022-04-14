package com.example.jf.features.articleAboutCity.data.api.response

data class Page(
    val extract: String,
    val ns: Int,
    val original: Original?,
    val pageid: Int,
    val title: String
)
