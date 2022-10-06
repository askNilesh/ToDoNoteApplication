package com.asknilesh.noteapplication.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.domain.use_case.NotesUseCases
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder
import com.asknilesh.noteapplication.feature_note.domain.util.OrderType
import com.asknilesh.noteapplication.feature_note.presentation.notes.NotesEvent.DeleteNote
import com.asknilesh.noteapplication.feature_note.presentation.notes.NotesEvent.Order
import com.asknilesh.noteapplication.feature_note.presentation.notes.NotesEvent.RestoreNote
import com.asknilesh.noteapplication.feature_note.presentation.notes.NotesEvent.ToggleOrderSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
  private val notesUseCases: NotesUseCases
) : ViewModel() {

  private val _state = mutableStateOf<NoteState>(NoteState())
  val state: State<NoteState> = _state

  private var recentlyDeletedNote: Note? = null

  private var getNotesJob: Job? = null

  init {
    getNotes(NoteOrder.Date(OrderType.Descending))
  }

  fun onEvent(notesEvent: NotesEvent) {
    when (notesEvent) {
      is Order -> {
        if (state.value.noteOrder::class == notesEvent.noteOrder::class &&
          state.value.noteOrder.orderType == notesEvent.noteOrder.orderType
        ) {
          return
        }
        getNotes(notesEvent.noteOrder)
      }
      is DeleteNote -> {
        viewModelScope.launch {
          notesUseCases.deleteNoteUseCase(notesEvent.note)
          recentlyDeletedNote = notesEvent.note
        }
      }
      RestoreNote -> {
        viewModelScope.launch {
          notesUseCases.addNoteUseCase.invoke(recentlyDeletedNote ?: return@launch)
          recentlyDeletedNote = null
        }
      }
      ToggleOrderSection -> {
        _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
      }
    }
  }

  private fun getNotes(noteOrder: NoteOrder) {
    getNotesJob?.cancel()
    getNotesJob = notesUseCases.getNotesUseCase(noteOrder)
      .onEach { notes ->
        _state.value = state.value.copy(
          notes = notes,
          noteOrder = noteOrder
        )
      }
      .launchIn(viewModelScope)
  }
}