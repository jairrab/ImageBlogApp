package com.jairrab.myapp.di

import android.content.Context
import android.content.SharedPreferences
import com.jairrab.myapp.di.module.AppModule
import com.jairrab.myapp.di.module.RepositoryModule
import com.jairrab.myapp.di.module.ViewModelFactoryModule
import com.jairrab.myapp.repo.LocalRepo
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.Toaster
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun context(): Context
    fun toaster(): Toaster
    fun sharedPreferences(): SharedPreferences
    fun remoteRepo(): RemoteRepo
    fun localRepo(): LocalRepo
}