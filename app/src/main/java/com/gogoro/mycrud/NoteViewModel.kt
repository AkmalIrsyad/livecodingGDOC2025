package com.gogoro.mycrud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gogoro.mycrud.data.local.entity.Note
import com.gogoro.mycrud.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class NoteViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {

    val notes = noteRepository.getAllNotes()

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

}
