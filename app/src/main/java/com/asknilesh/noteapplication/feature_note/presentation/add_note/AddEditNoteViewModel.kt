package com.asknilesh.noteapplication.feature_note.presentation.add_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asknilesh.noteapplication.feature_note.domain.model.InvalidNoteException
import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.domain.use_case.NotesUseCases
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.ChangeColor
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.ChangeContentFocus
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.ChangeTitleFocus
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.EnteredContent
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.EnteredTitle
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.SaveNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
  private val notesUseCases: NotesUseCases,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private var currentId: String? = null

  init {
    savedStateHandle.get<String>("NOTE_ID")?.let { noteId ->
      if (noteId.isNotEmpty()) {
        viewModelScope.launch {
          notesUseCases.getNoteUseCase(noteId)?.also {
            currentId = it.clientId
            _noteTitle.value = _noteTitle.value.copy(text = it.title, isHintVisible = false)
            _noteContent.value = _noteContent.value.copy(text = it.title, isHintVisible = false)
            _noteColor.value = it.color

          }
        }
      } else {
        currentId = UUID.randomUUID().toString()
      }

    }
  }

  private val _noteTitle = mutableStateOf(
    NoteTextFieldState(
      hint = "Enter title..."
    )
  )
  val noteTitle: State<NoteTextFieldState> = _noteTitle

  private val _noteContent = mutableStateOf(
    NoteTextFieldState(
      hint = "Enter Content"
    )
  )
  val noteContent: State<NoteTextFieldState> = _noteContent

  private val _noteColor = mutableStateOf(Note.notesColor.random().toArgb())
  val noteColor: State<Int> = _noteColor

  private val _eventFlow = MutableSharedFlow<UiEvent>()
  val eventFlow = _eventFlow.asSharedFlow()

  fun onEvent(event: AddEditNoteEvent) {
    when (event) {
      is ChangeColor -> _noteColor.value = event.color
      is ChangeTitleFocus -> {
        _noteTitle.value = _noteTitle.value.copy(
          isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
        )
      }
      is EnteredTitle -> {
        _noteTitle.value = _noteTitle.value.copy(text = event.noteTitle)
      }
      is ChangeContentFocus -> {
        _noteContent.value = _noteContent.value.copy(
          isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
        )
      }
      is EnteredContent -> {
        _noteContent.value = _noteContent.value.copy(text = event.noteContent)
      }
      SaveNote -> {
        viewModelScope.launch {
          try {
            notesUseCases.addNoteUseCase.invoke(
              Note(
                title = noteTitle.value.text,
                content = noteContent.value.text,
                color = noteColor.value,
                clientId = currentId!!,
                timeStamp = System.currentTimeMillis()
              )
            )

            _eventFlow.emit(UiEvent.SaveNote)
          } catch (exception: InvalidNoteException) {
            _eventFlow.emit(UiEvent.ShowSnackBar(message = exception.message ?: "UnKnown Error"))
          }
        }
      }
    }
  }

  sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
    object SaveNote : UiEvent()
  }
}