package com.ahmed.taskmanager.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ahmed.taskmanager.domain.model.AppTheme
import com.ahmed.taskmanager.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserImpl(
    private val context: Context
): LocalUserManager {

    override suspend fun saveAppTheme(appTheme: String) {
        context.datastore.edit { settings ->
            settings[PreferencesKeys.APP_THEME] = appTheme
        }
    }
    override fun readAppTheme(): Flow<String> {
        return context.datastore.data.map { preferences->
            preferences[PreferencesKeys.APP_THEME] ?: AppTheme.LIGHT_FIRST.name
        }
    }
}

// Get instance of data store
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "USER_SETTINGS")

// now we can simply access this data store with our context, But to save key value inside our data
// store preference we also need thing called (Preference keys)
private object PreferencesKeys{
    val APP_THEME = stringPreferencesKey("APP_THEME")

}