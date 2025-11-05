package com.gogoro.mycrud.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gogoro.mycrud.data.local.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Query("SELECT * FROM note WHERE finished = 1 ORDER BY createdAt DESC")
    fun getFinishedNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE finished = 0 ORDER BY createdAt DESC")
    fun getUnfinishedNotes(): Flow<List<Note>>
}
