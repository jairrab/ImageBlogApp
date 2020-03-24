package com.jairrab.myapp.utils

import android.content.Context
import android.widget.Toast

class Toaster(private val context: Context) {

    fun showToast(
        resId: Int,
        duration: Int = Toast.LENGTH_LONG
    ) {
        showToast(context.getString(resId), duration)
    }

    fun showToast(
        message: String?,
        duration: Int = Toast.LENGTH_LONG
    ) {
        Toast.makeText(context, message, duration).show()
    }
}