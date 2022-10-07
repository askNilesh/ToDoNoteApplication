package com.asknilesh.noteapplication.feature_note.presentation.add_note.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
  text: String,
  hint: String,
  modifier: Modifier = Modifier,
  isHintVisible: Boolean = true,
  onValueChange: (String) -> Unit,
  onFocusChange: (FocusState) -> Unit,
  textStyle: TextStyle = TextStyle(),
  singleLine: Boolean = true,
) {

  Box(
    modifier = modifier
  ) {
    BasicTextField(
      value = text, onValueChange = onValueChange,
      singleLine = singleLine,
      modifier = Modifier
        .fillMaxWidth()
        .onFocusChanged { onFocusChange(it) }
    )
    if (isHintVisible) {
      Text(text = hint, style = textStyle, color = Color.DarkGray)
    }

  }
}