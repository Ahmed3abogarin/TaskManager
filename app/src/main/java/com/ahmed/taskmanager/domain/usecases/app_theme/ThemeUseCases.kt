package com.ahmed.taskmanager.domain.usecases.app_theme

import com.ahmed.taskmanager.domain.usecases.app_entry.GetAppEntry
import com.ahmed.taskmanager.domain.usecases.app_entry.SaveAppEntry

data class ThemeUseCases(
    val readAppTheme: GetAppTheme,
    val saveAppTheme: SaveAppTheme,
    val getAppEntry: GetAppEntry,
    val saveAppEntry: SaveAppEntry
)