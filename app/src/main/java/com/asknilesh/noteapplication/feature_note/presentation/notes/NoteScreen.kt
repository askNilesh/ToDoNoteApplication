package com.asknilesh.noteapplication.feature_note.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.asknilesh.noteapplication.feature_note.presentation.notes.component.NoteItem
import com.asknilesh.noteapplication.feature_note.presentation.notes.component.OrderSection
import com.asknilesh.noteapplication.feature_note.presentation.util.Screen
import com.asknilesh.noteapplication.utils.Constants
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
  navController: NavController,
  viewModel: NotesViewModel = hiltViewModel()
) {
  val state = viewModel.state.value
  val scaffoldState = rememberScaffoldState()
  val scope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = scaffoldState,
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          navController.navigate(Screen.AddEditNoteScreen.route)
        },
        backgroundColor = MaterialTheme.colors.primary
      ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
      }
    }
  ) {

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "Your Note", style = MaterialTheme.typography.h4)
        IconButton(onClick = {
          viewModel.onEvent(NotesEvent.ToggleOrderSection)
        }) {
          Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort Note")
        }
      }

      AnimatedVisibility(
        visible = state.isOrderSectionVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
      ) {
        OrderSection(
          onOrderChange = {
            viewModel.onEvent(NotesEvent.Order(it))
          },
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
          noteOrder = state.noteOrder
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
      LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.notes) { note ->
          NoteItem(
            note = note,
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                navController.navigate(
                  Screen.AddEditNoteScreen.route +
                    "?${Constants.NOTE_ID}=${note.clientId}&${Constants.NOTE_COLOR}=${note.color}"
                )
              },
            onDeleteItem = {
              viewModel.onEvent(NotesEvent.DeleteNote(note))
              scope.launch {
                val result = scaffoldState.snackbarHostState.showSnackbar(
                  message = "Note Deleted",
                  actionLabel = "Undo"
                )
                if (result == ActionPerformed) {
                  viewModel.onEvent(NotesEvent.RestoreNote)
                }
              }
            }
          )

          Spacer(modifier = Modifier.height(16.dp))

        }
      }
    }

  }
}