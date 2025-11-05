package com.gogoro.mycrud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gogoro.mycrud.data.local.entity.Note
import com.gogoro.mycrud.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EditorViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote = _selectedNote.asStateFlow()

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            _selectedNote.value = noteRepository.getNoteById(id)
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

}
