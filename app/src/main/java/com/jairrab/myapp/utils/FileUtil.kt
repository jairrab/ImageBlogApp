package com.jairrab.myapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment
import com.jairrab.myapp.models.Post
import java.io.File
import javax.inject.Inject

class FileUtil @Inject constructor(
    private val context: Context
) {

    fun pickFile(
        fragment: Fragment,
        requestCode: Int,
        mimeType: MimeType,
        pickerInitialUri: Uri? = null
    ) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)

            type = mimeType.value

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }

        fragment.startActivityForResult(intent, requestCode)
    }

    fun getCacheFile(post: Post): File {
        return File(context.cacheDir, "${post.user}__${post.postName}")
    }
}