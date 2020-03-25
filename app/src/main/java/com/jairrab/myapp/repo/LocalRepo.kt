package com.jairrab.myapp.repo

import com.jairrab.myapp.models.Post

interface LocalRepo {
    suspend fun nukeTable()
    suspend fun saveData(post: Post)
    suspend fun updateData(post: Post)
    suspend fun getData(publicOnly: Boolean): List<Post>?
    suspend fun getData(user: String, publicOnly: Boolean): List<Post>?
    suspend fun deleteData(post: Post)
}