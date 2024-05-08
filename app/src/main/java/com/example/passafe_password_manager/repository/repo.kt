package com.example.passafe_password_manager.repository

import androidx.lifecycle.LiveData
import com.example.passafe_password_manager.data.local.dao
import com.example.passafe_password_manager.data.local.passData
import javax.inject.Inject

class repo @Inject constructor(
    private val dao: dao
) {
    suspend fun insertPass(passData: passData){
        dao.insertPass(passData)
    }
    fun getAllPass(): LiveData<List<passData>>{
        return dao.getAllData()
    }
    suspend fun getPassData(id: Int): LiveData<passData>{
        return dao.getData(id)
    }
    suspend fun updatePass(passData: passData){
        dao.updatePass(passData)
    }
    suspend fun deletePass(passData: passData){
        dao.deletePass(passData)
    }
}