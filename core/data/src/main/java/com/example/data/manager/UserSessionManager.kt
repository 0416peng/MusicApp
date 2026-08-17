package com.example.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class UserSessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val cookie = stringPreferencesKey("cookie")
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val cookieFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[cookie]
    }

    @Volatile
    var currentCookie: String? = null
        private set

    init {
        scope.launch {
            cookieFlow.collectLatest { currentCookie = it }
        }
    }

    suspend fun saveCookie(cookie: String) {
        currentCookie = cookie
        context.dataStore.edit { preferences ->
            preferences[this.cookie] = cookie
        }
    }

    suspend fun clearCookie() {
        currentCookie = null
        context.dataStore.edit { preferences ->
            preferences.remove(this.cookie)
        }
    }

}
