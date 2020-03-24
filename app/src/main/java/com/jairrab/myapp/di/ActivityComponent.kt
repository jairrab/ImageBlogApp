/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/11/27
 */

package com.jairrab.myapp.di

import com.jairrab.myapp.di.module.ActivityModule
import com.jairrab.myapp.di.scope.ActivityScope
import com.jairrab.myapp.view.main.MainActivity
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModelModule
import dagger.Component

@ActivityScope
@Component(
    modules = [
        ActivityViewModelModule::class,
        ActivityModule::class
    ],
    dependencies = [AppComponent::class]
)
interface ActivityComponent {

    fun inject(view: MainActivity)
}