/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/12/15
 */
package com.jairrab.myapp.utils

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

class UriUtil @Inject constructor(
    private val context: Context
) {

    suspend fun copyUriToFile(uri: Uri?, file: File) =
        copy(uri, file).also {
            if (file.exists()) {
                println("^^^ copied uri:${uri?.lastPathSegment} to file:$file")
            }
        }

    private suspend fun copy(uri: Uri?, outputFile: File): File? = coroutineScope {
        val outputUri = async {
            uri?.let {
                context.contentResolver.openFileDescriptor(uri, "r", null)
                    ?.let { FileInputStream(it.fileDescriptor) }
                    ?.let { inputStream ->
                        inputStream.copyTo(FileOutputStream(outputFile))
                        outputFile
                    }
            }
        }

        outputUri.await()
    }
}