package com.jairrab.myapp.repo.local

import com.jairrab.myapp.models.Post
import com.jairrab.myapp.repo.LocalRepo
import javax.inject.Inject

class RoomRepo @Inject constructor(
    private val database: AppDatabase,
    private val mapper: Mapper
) : LocalRepo {

    override suspend fun saveData(post: Post) {
        database.appDao.saveData(mapper.mapToCachedPost(post))
    }

    override suspend fun updateData(post: Post) {
        database.appDao.saveData(mapper.mapToCachedPost(post))
    }

    override suspend fun getData(publicOnly: Boolean): List<Post>? {
        return database.appDao.getData(publicOnly)
            ?.map { mapper.mapToPost(it) }
    }

    override suspend fun getData(user: String): List<Post>? {
        return database.appDao.getData(user)
            ?.map { mapper.mapToPost(it) }
    }

    override suspend fun deleteData(post: Post) {
        database.appDao.deleteData(mapper.mapToCachedPost(post))
    }
}