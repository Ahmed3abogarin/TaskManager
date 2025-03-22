package com.ahmed.taskmanager.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.taskmanager.domain.model.Priority
import com.ahmed.taskmanager.presentation.components.PriorityDropDown
import com.ahmed.taskmanager.presentation.components.TaskTextField
import com.ahmed.taskmanager.presentation.components.taskDatePicker
import com.ahmed.taskmanager.domain.model.Task


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    event: (CreateScreenEvent) -> Unit,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {

    var priority by remember { mutableStateOf(Priority.LOW) }

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {


            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(40.dp))

                Text(
                    text = "Add a Task",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Fill the details below to add a task into your tasks list",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
                TaskTextField(
                    placeHolder = "Title",
                    text = title
                )
                Spacer(modifier = Modifier.height(16.dp))

                TaskTextField(
                    lines = 9,
                    placeHolder = "Description",
                    text = description
                )
                Spacer(modifier = Modifier.height(16.dp))
                PriorityDropDown(priority = priority) {
                    priority = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                val dateResult = taskDatePicker(taskDate = "Due date")
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        if (title.value.isNotEmpty()) {
                            event(
                                CreateScreenEvent.UpsertTask(
                                    Task(
                                        title = title.value.trim(),
                                        description = description.value.trim(),
                                        priority = priority,
                                        dueDate = dateResult
                                    )
                                )
                            )
                        }

                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                ) {
                    Text(text = "Add Task", fontSize = 18.sp, modifier = Modifier.padding(4.dp))

                }
            }


        }
    }

}

