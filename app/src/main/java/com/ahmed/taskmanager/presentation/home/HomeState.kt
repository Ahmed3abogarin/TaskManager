package com.ahmed.taskmanager.presentation.home

import com.ahmed.taskmanager.domain.model.Task

data class HomeState(
    val isLoading: Boolean = true,
    val taskCount: Int = 0,
    val completedTasks: Int = 0,
    val tasks: List<Task> = mutableListOf(),

    val sortOrder: Int = 0,
    val filter: Int = 0
)