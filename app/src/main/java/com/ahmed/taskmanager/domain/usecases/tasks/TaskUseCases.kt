package com.ahmed.taskmanager.domain.usecases.tasks

data class TaskUseCases(
    val upsertTask: UpsertTask,
    val deleteTask: DeleteTask,
    val getTasks: GetTasks,
    val getByLowPriority: GetByLowPriority,
    val getByHighPriority: GetByHighPriority
)