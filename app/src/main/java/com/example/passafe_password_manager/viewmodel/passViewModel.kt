package com.example.passafe_password_manager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passafe_password_manager.data.local.dao
import com.example.passafe_password_manager.data.local.passData
import com.example.passafe_password_manager.repository.repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class passViewModel @Inject constructor(private val repo: repo) : ViewModel() {

    fun insertData(passData: passData){
        viewModelScope.launch {
            repo.insertPass(passData)
        }
    }
    fun getAllData(): LiveData<List<passData>> {
        return repo.getAllPass()
    }
    suspend fun editData(passData: passData){
        repo.updatePass(passData)
    }
    suspend fun deleteData(passData: passData){
        repo.deletePass(passData)
    }
    suspend fun getData(id: Int): LiveData<passData> {
        return repo.getPassData(id)
    }
}