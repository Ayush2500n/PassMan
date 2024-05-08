package com.example.passafe_password_manager.di

import android.content.Context
import androidx.room.Room
import com.example.passafe_password_manager.data.local.dao
import com.example.passafe_password_manager.data.local.database
import com.example.passafe_password_manager.data.local.database.Companion.migration1to2
import com.example.passafe_password_manager.data.local.database.Companion.migration2to3
import com.example.passafe_password_manager.repository.repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appModule {

    @Provides
    @Singleton
    fun providePassDao(database: database): dao{
        return  database.getDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext
        context: Context): database{
        var instance: database? = null
            synchronized(context) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        database::class.java,
                        "password_data"
                    ).addMigrations(migration1to2, migration2to3)
                        .build()
                }
            }
            return instance!!
    }

    @Provides
    @Singleton
    fun provideRepo(dao: dao): repo{
        return repo(dao)
    }

}