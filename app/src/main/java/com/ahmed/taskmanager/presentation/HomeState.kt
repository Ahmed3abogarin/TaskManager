package com.ahmed.taskmanager.presentation

import com.ahmed.taskmanager.domain.model.Task

data class HomeState(
    val tasks: List<Task> = mutableListOf()
)