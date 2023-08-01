package com.example.myapplication.core.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myapplication.core.data.api.response.item.LoginItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionRepository(private val dataStore: DataStore<Preferences>) {
    fun getUser(): Flow<LoginItem> {
        return dataStore.data.map {
            LoginItem(
                it[TOKEN_KEY] ?: "",
                it[USERID_KEY] ?: 0,
                it[STATE_KEY] ?: false,
            )
        }
    }

    suspend fun saveUser(user: LoginItem) {
        dataStore.edit {
            it[TOKEN_KEY] = user.token
            it[USERID_KEY] = user.user_id
            it[STATE_KEY] = user.state
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[TOKEN_KEY] = ""
            it[USERID_KEY] = 0
            it[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionRepository? = null
        private val USERID_KEY = intPreferencesKey("user_id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): SessionRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionRepository(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}