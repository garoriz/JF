package com.example.jf.features.notes.domain.useCases

import android.content.Context
import com.example.jf.features.notes.domain.model.Note
import com.example.jf.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val db: AppDatabase
) {
    suspend operator fun invoke(note: Note) {
        return withContext(Dispatchers.Main) {
            db.noteDao().deleteAll()
            db.noteDao().save(note)
        }
    }
}
