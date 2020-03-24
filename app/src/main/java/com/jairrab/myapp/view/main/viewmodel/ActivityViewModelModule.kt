package com.jairrab.myapp.view.main.viewmodel

import androidx.lifecycle.ViewModel
import com.jairrab.myapp.di.scope.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Enable injecting dependencies to ViewModel Constructor
 * @see [https://stackoverflow.com/a/49087002/7960756](https://stackoverflow.com/a/49087002/7960756)
 */
@Module
abstract class ActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ActivityViewModel::class)
    abstract fun bindActivityViewModel(viewModel: ActivityViewModel): ViewModel
}
