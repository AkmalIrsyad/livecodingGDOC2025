package com.gogoro.mycrud.data.repository

import com.gogoro.mycrud.data.local.dao.NoteDao
import com.gogoro.mycrud.data.local.entity.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    fun getFinishedNotes(): Flow<List<Note>> = noteDao.getFinishedNotes()

    fun getUnfinishedNotes(): Flow<List<Note>> = noteDao.getUnfinishedNotes()

    suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}
