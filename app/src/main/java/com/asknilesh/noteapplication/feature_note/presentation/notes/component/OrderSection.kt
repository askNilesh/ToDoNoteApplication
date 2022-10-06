package com.asknilesh.noteapplication.feature_note.presentation.notes.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder.Color
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder.Date
import com.asknilesh.noteapplication.feature_note.domain.util.NoteOrder.Title
import com.asknilesh.noteapplication.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
  modifier: Modifier = Modifier,
  noteOrder: NoteOrder = Date(OrderType.Descending),
  onOrderChange: (NoteOrder) -> Unit
) {
  Column(modifier = modifier) {
    Row(modifier = Modifier.fillMaxWidth()) {
      DefaultRadioButton(
        text = "Title",
        selected = noteOrder is Title,
        onSelect = { onOrderChange(Title(noteOrder.orderType)) })

      Spacer(modifier = Modifier.width(8.dp))

      DefaultRadioButton(
        text = "Date",
        selected = noteOrder is Date,
        onSelect = { onOrderChange(Date(noteOrder.orderType)) })

      Spacer(modifier = Modifier.width(8.dp))

      DefaultRadioButton(
        text = "Color",
        selected = noteOrder is Color,
        onSelect = { onOrderChange(Color(noteOrder.orderType)) })

    }
    Spacer(modifier = Modifier.height(16.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
      DefaultRadioButton(
        text = "Ascending",
        selected = noteOrder.orderType is OrderType.Ascending,
        onSelect = { onOrderChange(noteOrder.copy(orderType = OrderType.Ascending)) })

      Spacer(modifier = Modifier.width(8.dp))

      DefaultRadioButton(
        text = "Descending",
        selected = noteOrder.orderType is OrderType.Descending,
        onSelect = { onOrderChange(noteOrder.copy(orderType = OrderType.Descending)) })

    }
  }
}