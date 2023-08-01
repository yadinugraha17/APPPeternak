package com.example.myapplication.core.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.data.api.response.item.LoginItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SessionViewModel(private val sessionRepository: SessionRepository) : ViewModel() {
    fun getUser(): Flow<LoginItem> {
        return sessionRepository.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            sessionRepository.logout()
        }
    }

    fun saveUser(user: LoginItem) {
        viewModelScope.launch {
            sessionRepository.saveUser(user)
        }
    }
}