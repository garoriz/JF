package com.example.jf.features.notes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jf.features.notes.domain.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE id = 1")
    suspend fun get(): Note?

    @Insert
    suspend fun save(note: Note)

    @Query("DELETE FROM note")
    suspend fun deleteAll()
}
