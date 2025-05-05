package com.ahmed.taskmanager.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ahmed.taskmanager.domain.model.AppTheme
import com.ahmed.taskmanager.domain.manager.LocalUserManager
import com.ahmed.taskmanager.util.Constants
import com.ahmed.taskmanager.util.Constants.USER_SETTINGS
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

    override suspend fun saveAppEntry() {
        context.datastore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.datastore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }
}

// Get instance of data store
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

// now we can simply access this data store with our context, But to save key value inside our data
// store preference we also need thing called (Preference keys)
private object PreferencesKeys{
    val APP_THEME = stringPreferencesKey(Constants.APP_THEME)
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)

}