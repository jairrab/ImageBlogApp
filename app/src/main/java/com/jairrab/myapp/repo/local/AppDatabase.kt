/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/11/27
 */

package com.jairrab.myapp.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CachedPost::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val appDao: AppDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "projects.db"
                    ).build()
                }
                return INSTANCE as AppDatabase
            }
            return INSTANCE as AppDatabase
        }
    }

}