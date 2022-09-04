package com.asknilesh.noteapplication.feature_note.data.repository

import com.asknilesh.noteapplication.feature_note.data.data_source.NoteDao
import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
  private val noteDao: NoteDao
) : NoteRepository {
  override fun getNotes(): Flow<List<Note>> {
    return noteDao.getNotes()
  }

  override suspend fun getNoteById(clientId: String): Note? {
    return noteDao.getNoteById(clientId)
  }

  override suspend fun insertNote(note: Note) {
    noteDao.insertNote(note)
  }

  override suspend fun deleteNote(note: Note) {
    noteDao.deleteNote(note = note)
  }
}