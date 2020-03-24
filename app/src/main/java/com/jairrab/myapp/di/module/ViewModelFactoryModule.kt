package com.jairrab.myapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.jairrab.myapp.view.viewmodelfactory.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Enable injecting dependencies to ViewModel Constructor
 * @see [https://stackoverflow.com/a/49087002/7960756](https://stackoverflow.com/a/49087002/7960756)
 */
@Module
abstract class ViewModelFactoryModule {
    /**
     * see https://medium.com/chili-labs/android-viewmodel-injection-with-dagger-f0061d3402ff
     * for reason behind Singleton scoping
     * Enable injecting dependencies to ViewModel Constructor
     * see https://stackoverflow.com/a/49087002/7960756
     */
    @Singleton
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
