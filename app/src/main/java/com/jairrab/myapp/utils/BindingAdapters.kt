package com.jairrab.myapp.utils

import android.view.View
import androidx.databinding.BindingAdapter


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:showView")
    fun showView(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}