package com.jairrab.myapp

import android.app.Activity
import android.app.Application
import android.content.Context
import com.jairrab.myapp.di.*
import com.jairrab.myapp.di.module.ActivityModule

class MyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    companion object {
        private fun appComponent(context: Context): AppComponent {
            return (context.applicationContext as MyApp).appComponent
        }

        fun activityComponent(context: Context): ActivityComponent {
            return DaggerActivityComponent
                .builder()
                .activityModule(
                    ActivityModule(
                        context as Activity
                    )
                )
                .appComponent(appComponent(context))
                .build()
        }

        fun fragmentComponent(context: Context): FragmentComponent {
            return DaggerFragmentComponent
                .builder()
                .activityModule(
                    ActivityModule(
                        context as Activity
                    )
                )
                .appComponent(appComponent(context))
                .build()
        }
    }
}