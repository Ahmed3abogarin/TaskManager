package com.ahmed.taskmanager.domain.usecases.tasks

import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.repository.TasksRepository

class DeleteTask(
    private val tasksRepository: TasksRepository,
) {
    suspend operator fun invoke(task: Task) {
        tasksRepository.deleteTask(task)
    }
}