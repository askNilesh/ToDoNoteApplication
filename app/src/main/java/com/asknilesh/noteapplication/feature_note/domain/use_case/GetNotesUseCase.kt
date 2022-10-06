package com.asknilesh.noteapplication.feature_note.domain.use_case

import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.domain.repository.NoteRepository
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder
import com.asknilesh.noteapplication.feature_note.domain.util.OrderType.Ascending
import com.asknilesh.noteapplication.feature_note.domain.util.OrderType.Descending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
  private val noteRepository: NoteRepository
) {

  operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(Descending)): Flow<List<Note>> {
    return noteRepository.getNotes().map { notes ->
      when (noteOrder.orderType) {
        is Ascending -> {
          when (noteOrder) {
            is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
            is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
            is NoteOrder.Color -> notes.sortedBy { it.color }
          }
        }
        is Descending -> {
          when (noteOrder) {
            is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
            is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
            is NoteOrder.Color -> notes.sortedByDescending { it.color }
          }
        }
      }
    }
  }
}