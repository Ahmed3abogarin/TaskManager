package com.ahmed.taskmanager.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {


    suspend fun saveAppTheme(appTheme: String)

    fun readAppTheme(): Flow<String>
}