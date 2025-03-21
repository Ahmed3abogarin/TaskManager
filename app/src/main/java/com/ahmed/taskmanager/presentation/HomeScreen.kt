package com.ahmed.taskmanager.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.taskmanager.TaskCircularProgress
import com.ahmed.taskmanager.common.EmptyScreen
import com.ahmed.taskmanager.common.TaskCard
import com.ahmed.taskmanager.common.TaskShimmerEffect
import com.ahmed.taskmanager.details.DetailsEvent
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.ui.theme.Orange


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onClick: (Task) -> Unit,
    event: (DetailsEvent) -> Unit,
) {
    val state = viewModel.state.value
    val tasks = state.tasks
    val progress = remember { mutableIntStateOf(0) }
    val completedTasks = tasks.count { it.done }
    val allTasks = tasks.size
    val uncompleted = allTasks - completedTasks

    LaunchedEffect(key1 = tasks.size, key2 = completedTasks) {
        progress.intValue = (completedTasks.toFloat() / allTasks.toFloat() * 100).toInt()
    }


    if (state.isLoading){
        Column {
            repeat(10){
                TaskShimmerEffect(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }


    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Your tasks", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "All tasks: $allTasks", color = Color.Gray, modifier = Modifier.padding(start = 8.dp))
                Text(text = "uncompleted tasks: $uncompleted", color = Color.Gray, modifier = Modifier.padding(start = 8.dp))
                Text(text = "completed tasks: $completedTasks", color = Color.Gray, modifier = Modifier.padding(start = 8.dp))
            }
            TaskCircularProgress(
                modifier = Modifier
                    .size(160.dp),
                initialValue = progress,
                primaryColor = Orange,
                secondaryColor = Color.DarkGray,
                circleRadius = 130f

            )
        }


        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "All tasks", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Icon(Icons.Default.Menu, contentDescription = null)

        }

        Spacer(modifier = Modifier.height(8.dp))


        if (state.tasks.isEmpty()){
            EmptyScreen()
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.tasks, key = { task -> task.id }) {
                val task = it
                TaskCard(
                    modifier = Modifier.animateItem(
                        fadeInSpec = null,
                        fadeOutSpec = null,
                        placementSpec = tween(200)
                    ),
                    task,
                    onClick = { onClick(task) },
                    onRadioClicked = { oldTask ->
                        val updateTask = Task(
                            id = oldTask.id,
                            title = oldTask.title,
                            description = oldTask.description,
                            dueDate = oldTask.dueDate,
                            priority = oldTask.priority,
                            done = !oldTask.done
                        )
                        event(DetailsEvent.UpsertTask(updateTask))
                    },
                    animatedVisibilityScope = animatedVisibilityScope,
                    onRemove = { event(DetailsEvent.DeleteTask(task)) }
                )
            }
        }
    }
}
