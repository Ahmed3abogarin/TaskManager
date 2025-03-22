package com.ahmed.taskmanager.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.common.PriorityDropDown
import com.ahmed.taskmanager.common.TaskTopAppBar
import com.ahmed.taskmanager.common.taskDatePicker
import com.ahmed.taskmanager.domain.model.Task


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    task: Task,
    animatedVisibilityScope: AnimatedVisibilityScope,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
) {
    var description by remember { mutableStateOf(task.description) }
    var priority by remember { mutableStateOf(task.priority) }
    var dateResult by remember { mutableStateOf(task.dueDate) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.tertiary)
            .sharedElement(
                state = rememberSharedContentState(key = "task ${task.id}"),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        TaskTopAppBar(
            title = task.title, taskBoolean = task.done, onDeleteClick = {
                event(DetailsEvent.DeleteTask(task))
                navigateUp()
            },
            onDoneClick = { title, radioStatus ->
                if (title.isNotEmpty()){
                    val updateTask = Task(
                        id = task.id,
                        title = title.trim(),
                        done = radioStatus,
                        description = description.trim(),
                        dueDate = dateResult,
                        priority = priority
                    )
                    event(DetailsEvent.UpsertTask(updateTask))
                    navigateUp()
                }

            })
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.Black),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp),
            value = description,
            minLines = 17,
            onValueChange = { description = it },
            placeholder = { Text(text = "Description") },
        )
        Spacer(modifier = Modifier.height(12.dp))

        PriorityDropDown(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            priority = priority
        ) {
            priority = it
        }
        Spacer(modifier = Modifier.height(12.dp))
        dateResult = taskDatePicker(
            taskDate = task.dueDate,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )


    }
}


