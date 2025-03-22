package com.ahmed.taskmanager.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.ahmed.taskmanager.ui.theme.Orange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopAppBar(title: String,taskBoolean: Boolean, onDoneClick: (String, Boolean) -> Unit,onDeleteClick: () -> Unit) {
    var changeTitle by remember { mutableStateOf(title) }
    var radioStatus by remember { mutableStateOf(taskBoolean) }
    TopAppBar(
        title = {
            TextField(
                placeholder = { Text(text = "Title") },
                value = changeTitle,
                onValueChange = { changeTitle = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                maxLines = 2
            )
        },
        navigationIcon = {
            RadioButton(selected = radioStatus,colors = RadioButtonDefaults.colors(
                disabledSelectedColor = Color.Black,
                disabledUnselectedColor = Color.Black,
                selectedColor = Orange, unselectedColor = Color.Black
            ), onClick = {
                radioStatus = !radioStatus
            })
        },
        actions = {
            IconButton(onClick = {onDeleteClick()}) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
            IconButton(onClick = { onDoneClick(changeTitle, radioStatus) }) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black)
            }
        }
    )

}
