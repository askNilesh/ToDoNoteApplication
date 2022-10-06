package com.asknilesh.noteapplication.feature_note.domain.use_case

import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
  private val repository: NoteRepository
) {

  suspend operator fun invoke(noteId: String): Note? {
    return repository.getNoteById(noteId)
  }
}