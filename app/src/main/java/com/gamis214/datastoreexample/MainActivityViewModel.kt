package com.gamis214.datastoreexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val context by lazy { getApplication<Application>().applicationContext}
    private val userData : MutableLiveData<UserData> = MutableLiveData()

    fun saveUserInDataStore(age: Int,name: String){
        viewModelScope.launch(Dispatchers.Main) {
            DataStore(context).storeUserInfo(age,name)
        }
    }

    fun getDataUser() : MutableLiveData<UserData>{
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                DataStore(context).getUserData()
            }.onSuccess {
                userData.postValue(it)
            }.onFailure {
                userData.postValue(null)
            }
        }
        return userData
    }

}