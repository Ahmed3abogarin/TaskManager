package com.ahmed.taskmanager.di

import android.app.Application
import androidx.room.Room
import com.ahmed.taskmanager.data.TasksDao
import com.ahmed.taskmanager.data.TasksDatabase
import com.ahmed.taskmanager.data.repository.TasksRepositoryImpl
import com.ahmed.taskmanager.domain.repository.TasksRepository
import com.ahmed.taskmanager.domain.usecases.DeleteTask
import com.ahmed.taskmanager.domain.usecases.GetByHighPriority
import com.ahmed.taskmanager.domain.usecases.GetByLowPriority
import com.ahmed.taskmanager.domain.usecases.GetTasks
import com.ahmed.taskmanager.domain.usecases.TaskUseCases
import com.ahmed.taskmanager.domain.usecases.UpsertTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): TasksDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = TasksDatabase::class.java,
            name = "Tasks"
        ).fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideDao(
        tasksDatabase: TasksDatabase,
    ): TasksDao = tasksDatabase.tasksDao

    @Provides
    @Singleton
    fun provideTasksRepository(tasksDao: TasksDao): TasksRepository = TasksRepositoryImpl(tasksDao)


    @Provides
    @Singleton
    fun provideUseCases(tasksRepository: TasksRepository): TaskUseCases = TaskUseCases(
        upsertTask = UpsertTask(tasksRepository),
        deleteTask = DeleteTask(tasksRepository),
        getTasks = GetTasks(tasksRepository),
        getByLowPriority = GetByLowPriority(tasksRepository),
        getByHighPriority = GetByHighPriority(tasksRepository)
    )

}