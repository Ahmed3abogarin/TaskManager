package com.ahmed.taskmanager.domain.usecases.app_theme

data class ThemeUseCases(
    val readAppTheme: GetAppTheme,
    val saveAppTheme: SaveAppTheme
)