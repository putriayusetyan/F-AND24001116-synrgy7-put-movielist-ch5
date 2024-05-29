package com.example.challenge5.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager(private val context: Context) {
    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val LOGGED_IN_KEY = booleanPreferencesKey("logged_in")
    }

    val username: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }

    val loggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[LOGGED_IN_KEY] ?: false
    }

    suspend fun saveUser(username: String, loggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[LOGGED_IN_KEY] = loggedIn
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
