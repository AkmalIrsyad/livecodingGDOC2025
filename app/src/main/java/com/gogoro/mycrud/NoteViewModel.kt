package com.gogoro.mycrud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gogoro.mycrud.data.local.entity.Note
import com.gogoro.mycrud.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.getAllNotes().collectLatest { notes ->
                _state.update { it.copy(notes = notes) }
            }
        }
    }

}
