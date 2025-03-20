package com.ahmed.taskmanager.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskTextField(modifier: Modifier = Modifier, lines: Int = 1, placeHolder: String, text: MutableState<String>){
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.Black),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth(),
        value = text.value,
        minLines = lines,
        onValueChange = {text.value = it},
        label = { Text(text = placeHolder) },
    )

}