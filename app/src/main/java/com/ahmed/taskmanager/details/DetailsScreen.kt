package com.ahmed.taskmanager.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.common.TaskTopAppBar
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.ui.theme.LightBlue


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsScreen(
    task: Task,
    animatedVisibilityScope: AnimatedVisibilityScope,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    var description by remember { mutableStateOf(task.description) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(LightBlue)
            .sharedElement(
                state = rememberSharedContentState(key = "task ${task.id}"),
                animatedVisibilityScope = animatedVisibilityScope
            )
    ) {
        TaskTopAppBar(title = task.title, taskBoolean = task.done, onDoneClick = { title, radioStatus ->
            val updateTask = Task(
                id = task.id,
                title = title,
                done = radioStatus,
                description = description,
                dueDate = task.dueDate,
                priority = task.priority
            )
            event(DetailsEvent.UpsertTask(updateTask))
            navigateUp()
        })
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        TextField(
            placeholder = { Text(text = "Description") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp),
            value = description,
            onValueChange = { description = it },
        )
    }

}


