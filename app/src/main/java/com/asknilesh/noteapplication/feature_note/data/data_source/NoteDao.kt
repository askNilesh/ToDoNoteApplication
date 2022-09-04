package com.asknilesh.noteapplication.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asknilesh.noteapplication.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

  @Query("SELECT * from note")
  fun getNotes(): Flow<List<Note>>

  @Query("SELECT * FROM note WHERE clientId = :clientId")
  suspend fun getNoteById(clientId: String): Note?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNote(note: Note)

  @Delete
  suspend fun deleteNote(note: Note)
}