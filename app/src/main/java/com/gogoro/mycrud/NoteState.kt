package com.gogoro.mycrud

import com.gogoro.mycrud.data.local.entity.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
)
