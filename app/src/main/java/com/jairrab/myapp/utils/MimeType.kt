/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/12/18
 */

package com.jairrab.myapp.utils

enum class MimeType(val value:String) {
    ALL("*/*"),
    PDF("application/pdf"),
    IMAGES("image/*"),
    CSV("text/csv"),
    HTML( "text/html"),
}

fun String.mimeType(): String? {
    return when (this) {
        "jpg", "png" -> "image/*"
        "csv"        -> "text/csv"
        "html"       -> "text/html"
        "pdf"        -> "application/pdf"
        else         -> null
    }
}