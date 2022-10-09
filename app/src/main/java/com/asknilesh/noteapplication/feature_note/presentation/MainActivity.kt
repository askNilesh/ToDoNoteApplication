package com.asknilesh.noteapplication.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asknilesh.noteapplication.feature_note.presentation.add_note.AddEditNoteScreen
import com.asknilesh.noteapplication.feature_note.presentation.notes.NoteScreen
import com.asknilesh.noteapplication.feature_note.presentation.util.Screen
import com.asknilesh.noteapplication.ui.theme.NoteAppTheme
import com.asknilesh.noteapplication.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      NoteAppTheme {
        androidx.compose.material.Surface(
          color = MaterialTheme.colors.background,

          ) {
          val navController = rememberNavController()
          NavHost(
            navController = navController,
            startDestination = Screen.NoteScreen.route,
          ) {
            composable(route = Screen.NoteScreen.route) {
              NoteScreen(navController = navController)
            }
            composable(
              route = Screen.AddEditNoteScreen.route +
                "?${Constants.NOTE_ID}={NOTE_ID}&${Constants.NOTE_COLOR}={NOTE_COLOR}",
              arguments = listOf(
                navArgument(name = Constants.NOTE_ID) {
                  type = NavType.StringType
                  defaultValue = ""
                },
                navArgument(name = Constants.NOTE_COLOR) {
                  type = NavType.IntType
                  defaultValue = -1
                }
              )
            ) {
              val color = it.arguments?.getInt(Constants.NOTE_COLOR) ?: -1
              AddEditNoteScreen(navController = navController, noteColor = color)
            }
          }
        }
      }
    }
  }
}
