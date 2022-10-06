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
                "?NOTE_ID={noteID}&NOTE_COLOR={noteColor}",
              arguments = listOf(
                navArgument(name = "NOTE_ID") {
                  type = NavType.StringType
                  defaultValue = ""
                },
                navArgument(name = "NOTE_COLOR") {
                  type = NavType.IntType
                  defaultValue = -1
                }
              )
            ) {
              val color = it.arguments?.getInt("NOTE_COLOR") ?: -1
              AddEditNoteScreen(navController = navController, noteColor = color)
            }
          }
        }
      }
    }
  }
}
