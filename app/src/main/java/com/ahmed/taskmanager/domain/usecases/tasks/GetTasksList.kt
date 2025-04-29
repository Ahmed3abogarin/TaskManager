package com.ahmed.taskmanager.domain.usecases.tasks

import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

class GetTasksList(
    private val tasksRepository: TasksRepository
) {
    operator fun invoke(filter: Int, sortOrder: Int): Flow<List<Task>> {
        return tasksRepository.getTasksList(filter,sortOrder)
    }
}