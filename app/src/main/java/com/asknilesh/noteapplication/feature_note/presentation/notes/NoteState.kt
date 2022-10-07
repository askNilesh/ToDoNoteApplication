package com.asknilesh.noteapplication.feature_note.presentation.notes

import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder
import com.asknilesh.noteapplication.feature_note.domain.util.OrderType.Descending

data class NoteState(
  val notes: List<Note> = emptyList(),
  val noteOrder: NoteOrder = NoteOrder.Date(Descending),
  val isOrderSectionVisible: Boolean = false
)