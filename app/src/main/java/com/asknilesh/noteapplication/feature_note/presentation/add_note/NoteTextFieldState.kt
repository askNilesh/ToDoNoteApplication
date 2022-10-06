package com.asknilesh.noteapplication.feature_note.presentation.add_note

data class NoteTextFieldState(
  val text: String = "",
  val hint: String = "",
  val isHintVisible: Boolean = true,
)
