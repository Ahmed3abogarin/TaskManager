package com.ahmed.taskmanager.domain.usecases.tasks

data class TaskUseCases(
    val upsertTask: UpsertTask,
    val deleteTask: DeleteTask,
    val getTasksList: GetTasksList
)