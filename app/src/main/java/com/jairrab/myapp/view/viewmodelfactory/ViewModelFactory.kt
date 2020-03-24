package com.jairrab.myapp.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider

/**
 * https://medium.com/@marco_cattaneo/android-viewmodel-and-factoryprovider-good-way-to-manage-it-with-dagger-2-d9e20a07084c
 **/
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val map: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        (map[modelClass]
         ?: map.asIterable()
             .firstOrNull { modelClass.isAssignableFrom(it.key) }?.value)
            ?.get() as? T
        ?: throw IllegalArgumentException("unknown model class $modelClass")
}