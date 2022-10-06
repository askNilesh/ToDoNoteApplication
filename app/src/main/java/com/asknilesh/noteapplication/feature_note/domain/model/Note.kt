package com.asknilesh.noteapplication.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asknilesh.noteapplication.ui.theme.BabyBlue
import com.asknilesh.noteapplication.ui.theme.LightGreen
import com.asknilesh.noteapplication.ui.theme.RedOrange
import com.asknilesh.noteapplication.ui.theme.RedPink
import com.asknilesh.noteapplication.ui.theme.Violet

@Entity
data class Note(
  @PrimaryKey
  val clientId: String,
  val title: String,
  val content: String,
  val timeStamp: Long,
  val color: Int
) {
  companion object {
    val notesColor = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
  }
}

class InvalidNoteException(message: String) : Exception(message)