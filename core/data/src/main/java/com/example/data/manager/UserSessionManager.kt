package com.example.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class UserSessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val cookie = stringPreferencesKey("cookie")
    val cookieFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[cookie]
    }

    suspend fun saveCookie(cookie: String) {
        context.dataStore.edit { preferences ->
            preferences[this.cookie] = cookie
        }
    }

    suspend fun clearCookie() {
        context.dataStore.edit { preferences ->
            preferences.remove(this.cookie)
        }
    }

}