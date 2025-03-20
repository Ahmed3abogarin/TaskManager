package com.ahmed.taskmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmed.taskmanager.domain.model.Task

@Database(entities = [Task::class], version = 3)
abstract class TasksDatabase : RoomDatabase() {
    abstract val tasksDao: TasksDao
}