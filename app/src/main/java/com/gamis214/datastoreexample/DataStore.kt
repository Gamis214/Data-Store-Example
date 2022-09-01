package com.gamis214.datastoreexample

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_prefs")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
    }

    suspend fun storeUserInfo(age: Int, name: String){
        context.dataStore.edit { preferences ->
            preferences[USER_AGE_KEY] = age
            preferences[USER_NAME_KEY] = name
        }
    }

    suspend fun getUserData() : UserData{
        return UserData(
            userNameFlow.first(),
            userAgeFlow.first()
        )
    }

    private val userAgeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[USER_AGE_KEY] ?: 0
    }

    private val userNameFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY] ?: ""
    }

}