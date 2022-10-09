package com.asknilesh.noteapplication.feature_note.presentation.add_note

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.asknilesh.noteapplication.feature_note.domain.model.Note
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.ChangeContentFocus
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.ChangeTitleFocus
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.EnteredContent
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteEvent.EnteredTitle
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteViewModel.UiEvent.SaveNote
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteViewModel.UiEvent.ShowSnackBar
import com.asknilesh.noteapplication.feature_note.presentation.add_note.component.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
  navController: NavController,
  noteColor: Int,
  viewModel: AddEditNoteViewModel = hiltViewModel()
) {
  val titleState = viewModel.noteTitle.value
  val contentState = viewModel.noteContent.value

  val scaffoldState = rememberScaffoldState()
  val scope = rememberCoroutineScope()

  val noteBackGroundAnimatable = remember {
    Animatable(
      Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
    )
  }

  LaunchedEffect(key1 = true) {
    viewModel.eventFlow.collectLatest { event ->
      when (event) {
        SaveNote -> navController.navigateUp()
        is ShowSnackBar -> {
          scaffoldState.snackbarHostState.showSnackbar(event.message)
        }
      }
    }
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          viewModel.onEvent(AddEditNoteEvent.SaveNote)
        },
        backgroundColor = MaterialTheme.colors.primary
      ) {
        Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note")
      }
    },
    scaffoldState = scaffoldState
  ) {

    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(noteBackGroundAnimatable.value)
        .padding(16.dp)
    ) {

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Note.notesColor.forEach { color ->
          val colorInt = color.toArgb()

          Box(
            modifier = Modifier
              .size(50.dp)
              .shadow(15.dp, CircleShape)
              .clip(CircleShape)
              .background(color)
              .border(
                width = 3.dp,
                color = if (viewModel.noteColor.value == colorInt) Color.Black else
                  Color.Transparent,
                shape = CircleShape
              )
              .clickable {
                scope.launch {
                  noteBackGroundAnimatable.animateTo(
                    targetValue = Color(colorInt),
                    animationSpec = tween(500)
                  )
                }
                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
              }
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
      TransparentHintTextField(
        text = titleState.text,
        hint = titleState.hint,
        onValueChange = {
          viewModel.onEvent(EnteredTitle(it))
        },
        onFocusChange = {
          viewModel.onEvent(ChangeTitleFocus(it))
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.h5
      )
      Spacer(modifier = Modifier.height(16.dp))

      TransparentHintTextField(
        text = contentState.text,
        hint = contentState.hint,
        onValueChange = {
          viewModel.onEvent(EnteredContent(it))
        },
        onFocusChange = {
          viewModel.onEvent(ChangeContentFocus(it))
        },
        textStyle = MaterialTheme.typography.body1,
        modifier = Modifier.fillMaxHeight()
      )

    }

  }
}