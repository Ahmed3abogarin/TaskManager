package com.ahmed.taskmanager.domain.usecases.tasks

import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.repository.TasksRepository

class UpsertTask(
    private val tasksRepository: TasksRepository,
) {
    suspend operator fun invoke(task: Task) {
        tasksRepository.upsertTask(task)
    }
}