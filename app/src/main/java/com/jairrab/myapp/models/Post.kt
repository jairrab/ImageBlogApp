package com.jairrab.myapp.models

import android.net.Uri
import java.io.File

data class Post(
    var id: String = "",
    val user: String? = null,
    val data: Uri? = null,
    val postName: String? = null,
    var publicVisibility: Boolean = false,
    val date: Long = 0,
    var cacheFile: File? = null
)