package com.ahmed.taskmanager.di

import android.app.Application
import androidx.room.Room
import com.ahmed.taskmanager.data.local.TasksDao
import com.ahmed.taskmanager.data.local.TasksDatabase
import com.ahmed.taskmanager.data.manager.LocalUserImpl
import com.ahmed.taskmanager.data.repository.TasksRepositoryImpl
import com.ahmed.taskmanager.domain.usecases.app_theme.GetAppTheme
import com.ahmed.taskmanager.domain.usecases.app_theme.SaveAppTheme
import com.ahmed.taskmanager.domain.usecases.app_theme.ThemeUseCases
import com.ahmed.taskmanager.domain.manager.LocalUserManager
import com.ahmed.taskmanager.domain.repository.TasksRepository
import com.ahmed.taskmanager.domain.usecases.tasks.DeleteTask
import com.ahmed.taskmanager.domain.usecases.tasks.GetByHighPriority
import com.ahmed.taskmanager.domain.usecases.tasks.GetByLowPriority
import com.ahmed.taskmanager.domain.usecases.tasks.GetTasks
import com.ahmed.taskmanager.domain.usecases.tasks.GetTasksList
import com.ahmed.taskmanager.domain.usecases.tasks.TaskUseCases
import com.ahmed.taskmanager.domain.usecases.tasks.UpsertTask
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
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserImpl(application)

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
        getByHighPriority = GetByHighPriority(tasksRepository),
        getTasksList = GetTasksList(tasksRepository)
    )

    @Provides
    @Singleton
    fun provideThemeUseCases(localUserManager: LocalUserManager): ThemeUseCases = ThemeUseCases(
        readAppTheme = GetAppTheme(localUserManager),
        saveAppTheme = SaveAppTheme(localUserManager)
    )

}