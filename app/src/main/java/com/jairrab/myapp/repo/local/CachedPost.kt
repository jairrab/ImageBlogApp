/*
 * Copyright (C) 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Antonio Barria <jaybarria@gmail.com>, 2019/11/27
 */

package com.jairrab.myapp.repo.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POSTS_TABLE")
data class CachedPost(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "user") val user: String? = null,
    @ColumnInfo(name = "postName") val postName: String? = null,
    @ColumnInfo(name = "publicVisibility") val publicVisibility: Boolean = false,
    @ColumnInfo(name = "date")
val date: Long = 0
)