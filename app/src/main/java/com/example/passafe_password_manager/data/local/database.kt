package com.example.passafe_password_manager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [passData::class], version = 3, exportSchema = false)
abstract class database: RoomDatabase() {

    abstract fun getDao(): dao

    companion object{
        val migration1to2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
            }
        }
        val migration2to3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
//
//                db.execSQL("DELETE FROM password_data")
                db.execSQL("DELETE FROM password_data WHERE id NOT IN " +
                        "(SELECT MIN(id) FROM password_data GROUP BY accountName, userName)")
                db.execSQL("CREATE UNIQUE INDEX index_password_data_accountName_userName ON password_data (accountName, userName)")
//                // Recreate the table with the correct schema
//                db.execSQL(
//                    "CREATE TABLE IF NOT EXISTS `password_data` (" +
//                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                            "`accountName` TEXT NOT NULL, " +
//                            "`userName` TEXT NOT NULL, " +
//                            "`password` TEXT NOT NULL, " +
//                            "UNIQUE(`accountName`, `userName`))"
//                )
            }
        }


    }
}