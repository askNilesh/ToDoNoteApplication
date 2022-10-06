package com.asknilesh.noteapplication.feature_note.presentation.add_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
  data class EnteredTitle(val noteTitle: String) : AddEditNoteEvent()
  data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
  data class EnteredContent(val noteContent: String) : AddEditNoteEvent()
  data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
  data class ChangeColor(val color: Int) : AddEditNoteEvent()
  object SaveNote :AddEditNoteEvent()
}