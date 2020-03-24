package com.jairrab.myapp.view.main.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.repo.LocalRepo
import com.jairrab.myapp.repo.RemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo
) : ViewModel() {

    var loggedInUser: String? = null
    var currentUser: String? = null
    var currentPost: Post? = null

    fun start() {
        //add startup routines and initializations
    }

    suspend fun saveData(data: Uri?) {
        if (data == null) return
        val post = Post(
            user = currentUser,
            data = data,
            postName = data.lastPathSegment,
            publicVisibility = true,
            date = Calendar.getInstance().timeInMillis
        )
        val returnedPost = remoteRepo.saveData(post)
        withContext(Dispatchers.Default) {
            localRepo.saveData(returnedPost)
        }
    }

    suspend fun updateData(post: Post) {
        remoteRepo.updateData(post)
        withContext(Dispatchers.Default) {
            localRepo.saveData(post)
        }
    }

    suspend fun deleteData(post: Post) {
        remoteRepo.deleteData(post)
        withContext(Dispatchers.Default) {
            localRepo.deleteData(post)
        }
    }
}