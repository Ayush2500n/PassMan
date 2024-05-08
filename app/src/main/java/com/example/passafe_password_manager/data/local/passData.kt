package com.example.passafe_password_manager.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "password_data", indices = [Index(value = ["accountName","userName"], unique = true)])
data class passData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accountName: String = "",
    val userName: String = "",
    val password: String = ""
)
