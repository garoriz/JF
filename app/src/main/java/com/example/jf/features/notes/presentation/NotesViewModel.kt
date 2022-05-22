package com.example.jf.features.notes.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jf.features.notes.domain.model.Note
import com.example.jf.features.notes.domain.useCases.GetNoteUseCase
import com.example.jf.features.notes.domain.useCases.SaveNoteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    private var _note: MutableLiveData<Result<Note?>> = MutableLiveData()
    val note: LiveData<Result<Note?>> = _note

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun onGetNote() {
        viewModelScope.launch {
            try {
                val note = getNoteUseCase()
                _note.value = Result.success(note)
            } catch (ex: Exception) {
                _note.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun onSaveNote(note: Note) {
        viewModelScope.launch {
            try {
               saveNoteUseCase(note)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
