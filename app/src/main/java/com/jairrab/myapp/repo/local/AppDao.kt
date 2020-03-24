/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/11/28
 */

package com.jairrab.myapp.repo.local

import androidx.room.*

@Dao
abstract class AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveData(cachedPost: CachedPost)

    @Query(
        """
        SELECT * FROM POSTS_TABLE 
        WHERE publicVisibility = :publicOnly
        ORDER BY date DESC
        """
    )
    abstract fun getData(publicOnly: Boolean): List<CachedPost>?

    @Query(
        """
        SELECT * FROM POSTS_TABLE 
        WHERE user = :user
        ORDER BY date DESC
        """
    )
    abstract fun getData(user: String): List<CachedPost>?

    @Query(
        """
        SELECT * FROM POSTS_TABLE 
        WHERE user = :user AND publicVisibility = :publicOnly
        ORDER BY date DESC
        """
    )
    abstract fun getData(user: String, publicOnly: Boolean): List<CachedPost>?

    @Delete
    abstract fun deleteData(cachedPost: CachedPost)
}