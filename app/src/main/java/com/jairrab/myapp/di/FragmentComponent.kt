/*
 * Copyright (C) 2020 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2020/1/3
 */

package com.jairrab.myapp.di

import com.jairrab.myapp.di.module.ActivityModule
import com.jairrab.myapp.di.scope.ActivityScope
import com.jairrab.myapp.view.home.HomeView
import com.jairrab.myapp.view.login.LoginView
import com.jairrab.myapp.view.user.UserView
import dagger.Component

@ActivityScope
@Component(
    modules = [
        ActivityModule::class
    ],
    dependencies = [AppComponent::class]
)
interface FragmentComponent {
    fun inject(view: LoginView)
    fun inject(view: HomeView)
    fun inject(view: UserView)
}