package com.ahmed.taskmanager.domain.usecases.app_theme

import com.ahmed.taskmanager.domain.manager.LocalUserManager

class SaveAppTheme(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(appTheme: String){
        localUserManager.saveAppTheme(appTheme)
    }
}