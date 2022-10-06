package com.asknilesh.noteapplication.feature_note.presentation.notes

import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.domain.util.Descending
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder

data class NoteState(
  val notes: List<Note> = emptyList(),
  val noteOrder: NoteOrder = NoteOrder.Date(Descending),
  val isOrderSectionVisible: Boolean = false
)