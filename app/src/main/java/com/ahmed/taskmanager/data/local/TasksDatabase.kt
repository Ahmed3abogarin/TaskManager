package com.ahmed.taskmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmed.taskmanager.domain.model.Task

@Database(entities = [Task::class], version = 5)
@TypeConverters(DateTimeConverter::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract val tasksDao: TasksDao
}