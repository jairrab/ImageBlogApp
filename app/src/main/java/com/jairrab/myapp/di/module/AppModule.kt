package com.jairrab.myapp.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jairrab.myapp.utils.Toaster
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun providesToaster(context: Context): Toaster {
        return Toaster(context)
    }

    @Singleton
    @JvmStatic
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
