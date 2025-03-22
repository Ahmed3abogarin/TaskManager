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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.taskmanager.TaskCircularProgress
import com.ahmed.taskmanager.common.SortDropdown
import com.ahmed.taskmanager.common.EmptyScreen
import com.ahmed.taskmanager.common.TaskCard
import com.ahmed.taskmanager.common.TaskShimmerEffect
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.ui.theme.Orange


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onClick: (Task) -> Unit,
    event: (HomeEvent) -> Unit,
    onRemoveClicked: (Task) -> Unit
) {
    val state = viewModel.state.value
    val tasks = state.tasks
    val progress = remember { mutableIntStateOf(0) }
    val completedTasks = state.completedTasks
    val allTasks = state.taskCount
    val uncompleted = allTasks - completedTasks

    var currentImageSize by remember { mutableIntStateOf(160) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                val newImageSize = currentImageSize + delta
                val previousImageSize = currentImageSize
                currentImageSize =
                    newImageSize.coerceIn(0, 200)
                val consumed = currentImageSize - previousImageSize

                return Offset(0f, consumed.toFloat())
            }
        }
    }

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


    Column(modifier = modifier.fillMaxWidth().nestedScroll(nestedScrollConnection)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(currentImageSize.dp)
                .padding(start = 16.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "All tasks: $allTasks",
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "completed tasks: $completedTasks",
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "Pending tasks: $uncompleted",
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            TaskCircularProgress(
                modifier = Modifier
                    .size(currentImageSize.dp),
                initialValue = progress,
                primaryColor = MaterialTheme.colorScheme.primary,
                secondaryColor = Color.DarkGray,
                circleRadius = (currentImageSize - 35).toFloat()

            )
        }


        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Tasks", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            SortDropdown(onSortClicked = {
                event(HomeEvent.GetSortTasks(it))
            })

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
                        fadeInSpec = tween(200),
                        fadeOutSpec = tween(200),
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
                        event(HomeEvent.UpsertTask(updateTask))
                    },
                    animatedVisibilityScope = animatedVisibilityScope,
                    onRemove = { onRemoveClicked(task)}
                )
            }
        }
    }
}
