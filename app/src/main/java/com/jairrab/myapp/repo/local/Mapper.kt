package com.jairrab.myapp.repo.local

import com.jairrab.myapp.models.Post
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapToCachedPost(post: Post): CachedPost {
        return CachedPost(
            id = post.id,
            user = post.user,
            postName = post.postName,
            publicVisibility = post.publicVisibility,
            date = post.date
        )
    }

    fun mapToPost(post: CachedPost): Post {
        return Post(
            id = post.id,
            user = post.user,
            postName = post.postName,
            publicVisibility = post.publicVisibility,
            date = post.date
        )
    }
}