/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/12/5
 */

package com.jairrab.myapp.di.module

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun providesActivity(): Activity {
        return activity
    }
}