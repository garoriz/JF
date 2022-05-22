package com.example.jf.features.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note")
data class Note(
    @PrimaryKey
    val id: Int,
    val content: String?,
) {
    constructor(content: String?) :
            this(1, content)
}
