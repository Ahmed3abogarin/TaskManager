package com.ahmed.taskmanager.presentation.details

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.ahmed.taskmanager.domain.model.Priority
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.presentation.components.TaskDateTimePicker
import com.ahmed.taskmanager.presentation.components.TaskTopAppBar
import com.ahmed.taskmanager.util.Dimens.PRIORITY_INDICATOR_SIZE


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    task: Task,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
    showDialog: MutableState<Boolean>,
) {
    var description by remember { mutableStateOf(task.description) }
    var priority by remember { mutableStateOf(task.priority) }
    var taskDate by remember { mutableStateOf(task.dueDate) }
    var taskTime by remember { mutableStateOf(task.time) }


    val priorities = remember { Priority.entries.toTypedArray() }


    if (showDialog.value) {
        BasicAlertDialog(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(14.dp)),
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)

            ) {
                TaskTopAppBar(
                    title = task.title, taskBoolean = task.done, onDeleteClick = {
                        event(DetailsEvent.DeleteTask(task))
                        navigateUp()
                    },
                    onDoneClick = { title, radioStatus ->
                        if (title.isNotEmpty()) {
                            val updateTask = Task(
                                id = task.id,
                                title = title.trim(),
                                done = radioStatus,
                                description = description.trim(),
                                dueDate = taskDate,
                                priority = priority,
                                time = taskTime
                            )
                            event(DetailsEvent.UpsertTask(updateTask))
                            navigateUp()
                        }

                    })
                HorizontalDivider(modifier = Modifier.fillMaxWidth())

                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 4.dp),
                    value = description,
                    minLines = 5,
                    onValueChange = { description = it },
                    placeholder = { Text(text = "Description") },
                )
                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(start = 16.dp,end = 16.dp))

                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(color = priority.color)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        onClick = {
                            val current = priorities.indexOf(priority)
                            val next = (current + 1) % priorities.size
                            priority = priorities[next]
                        }) {
                        Text(priority.name, color = Color.Black, fontSize = 20.sp)
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TaskDateTimePicker(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    onDateChanged = { taskDate = it },
                    onTimeChanged = { taskTime = it }
                )
                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(start = 16.dp,end = 16.dp))

                Spacer(modifier = Modifier.height(8.dp))


//        dateResult = taskDatePicker(
//            taskDate = task.dueDate,
//            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
//        )


            }

        }
    }


}


