package com.example.passafe_password_manager.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface dao {
    @Query("Select * from password_data order by accountName asc")
    fun getAllData(): LiveData<List<passData>>

    @Query("Select * from password_data where id = :id")
    fun getData(id: Int): LiveData<passData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPass(passData: passData)

    @Update
    suspend fun updatePass(passData: passData)

    @Delete
    suspend fun deletePass(passData: passData)
}