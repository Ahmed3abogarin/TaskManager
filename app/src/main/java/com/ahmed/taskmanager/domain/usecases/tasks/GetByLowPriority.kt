package com.ahmed.taskmanager.domain.usecases.tasks

import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

class GetByLowPriority(
    private val tasksRepository: TasksRepository
) {
    operator fun invoke():Flow<List<Task>>{
        return tasksRepository.getByLowPriority()
    }
}