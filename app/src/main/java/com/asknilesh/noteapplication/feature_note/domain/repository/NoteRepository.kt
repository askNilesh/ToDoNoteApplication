package com.asknilesh.noteapplication.feature_note.domain.repository

import com.asknilesh.noteapplication.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

  fun getNotes(): Flow<List<Note>>
  suspend fun getNoteById(clientId: String): Note?
  suspend fun insertNote(note: Note)
  suspend fun deleteNote(note: Note)
}