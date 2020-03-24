package com.jairrab.myapp.repo

import com.jairrab.myapp.models.Post
import java.io.File

interface RemoteRepo {
    suspend fun saveData(post: Post): Post
    suspend fun updateData(post: Post)
    suspend fun getData(publicOnly: Boolean): List<Post>?
    suspend fun getData(user: String): List<Post>?
    suspend fun deleteData(post: Post)
    suspend fun getImage(post: Post): File
}