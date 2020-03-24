package com.jairrab.myapp.repo

import com.jairrab.myapp.models.Post
import kotlinx.coroutines.flow.Flow

interface LocalRepo {
    suspend fun saveData(post: Post)
    suspend fun updateData(post: Post)
    suspend fun getData(publicOnly: Boolean): List<Post>?
    suspend fun getData(user: String): List<Post>?
    suspend fun deleteData(post: Post)
}