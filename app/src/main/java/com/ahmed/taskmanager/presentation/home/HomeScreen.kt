package com.ahmed.taskmanager.presentation.home

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.presentation.components.TaskCircularProgress
import com.ahmed.taskmanager.presentation.components.SortDropdown
import com.ahmed.taskmanager.presentation.components.EmptyScreen
import com.ahmed.taskmanager.presentation.components.TaskCard
import com.ahmed.taskmanager.presentation.components.TaskShimmerEffect
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.presentation.components.FilterDropDown


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    state: HomeState,
    onClick: (Task) -> Unit,
    event: (HomeEvent) -> Unit,
    onRemoveClicked: (Task) -> Unit
) {
    val tasks = state.tasks
    val progress = remember { mutableIntStateOf(0) }
    val completedTasks = tasks.filter { it.done }
    val uncompleted = tasks.size - completedTasks.size


    LaunchedEffect(key1 = tasks.size, key2 = completedTasks) {
        progress.intValue = (completedTasks.size.toFloat() / tasks.size.toFloat() * 100).toInt()
    }


    if (state.isLoading){
        Column {
            repeat(10){
                TaskShimmerEffect(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }


    Column(modifier = modifier.fillMaxWidth()) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "All tasks: ${tasks.size}",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Text(
                            text = "completed tasks: ${completedTasks.size}",
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
                        initialValue = progress,
                        primaryColor = MaterialTheme.colorScheme.primary,
                        secondaryColor = Color.DarkGray,
                        circleRadius = 160f

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
                    FilterDropDown (onFilterClicked = {
                        event(HomeEvent.UpdateTaskFilter(it))
                        Log.v("TTTOOL","Filter: $it")
                    })

                    SortDropdown(onSortClicked = {
                        event(HomeEvent.UpdateTaskSort(it))
                        Log.v("TTTOOL","Sort: $it")

                    })

                }

                Spacer(modifier = Modifier.height(8.dp))

                if (tasks.isEmpty()){
                    EmptyScreen()
                }
            }
            items(tasks, key = { task -> task.id }) {
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
                        val updateTask = oldTask.copy(done = !oldTask.done)

                        event(HomeEvent.UpsertTask(updateTask))
                    },
                    animatedVisibilityScope = animatedVisibilityScope,
                    onRemove = { onRemoveClicked(task)}
                )
            }
        }
    }
}
