/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/12/5
 */

package com.jairrab.myapp.di.module

import android.content.Context
import com.jairrab.myapp.repo.LocalRepo
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.repo.local.AppDatabase
import com.jairrab.myapp.repo.local.RoomRepo
import com.jairrab.myapp.repo.remote.FirebaseSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRemoteRemote(firebaseSource: FirebaseSource): RemoteRepo

    @Singleton
    @Binds
    abstract fun bindLocalRepo(roomRepo: RoomRepo): LocalRepo

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }
    }
}