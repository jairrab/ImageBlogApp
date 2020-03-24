package com.jairrab.myapp.repo.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.FileUtil
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class FirebaseSource @Inject constructor(
    private val fileUtil: FileUtil
) : RemoteRepo {

    override suspend fun saveData(post: Post): Post {
        val doc = hashMapOf(
            "user" to post.user,
            "postName" to post.postName,
            "date" to post.date,
            "publicVisibility" to post.publicVisibility
        )

        val id = FirebaseFirestore.getInstance().collection("posts")
            .add(doc)
            .await()
            .id

        FirebaseStorage.getInstance()
            .reference.child("${post.user}/$id").putFile(post.data!!)
            .await()

        post.id = id
        return post
    }

    override suspend fun updateData(post: Post) {
        val doc = hashMapOf(
            "user" to post.user,
            "postName" to post.postName,
            "date" to post.date,
            "publicVisibility" to post.publicVisibility
        )

        FirebaseFirestore.getInstance().collection("posts")
            .document(post.id)
            .set(doc)
    }

    override suspend fun getData(publicOnly: Boolean): List<Post>? {
        return FirebaseFirestore.getInstance().collection("posts")
            .let { if (publicOnly) it.whereEqualTo("publicVisibility", true) else it }
            .get()
            .await()
            .map {
                val post = it.toObject(Post::class.java)
                post.id = it.id
                post
            }
            .sortedByDescending { it.date }
    }

    override suspend fun getImage(post: Post): File {
        val file = fileUtil.getCacheFile(post)
        if (!file.exists()) {
            FirebaseStorage.getInstance().reference
                .child("${post.user}/${post.id}")
                .getFile(file)
                .await()
        }
        return file
    }

    override suspend fun deleteData(post: Post) {
        FirebaseFirestore.getInstance().collection("posts")
            .document(post.id)
            .delete()
            .await()

        FirebaseStorage.getInstance()
            .reference.child("${post.user}/${post.id}")
            .delete()
            .await()
    }

    override suspend fun getData(user: String, publicOnly: Boolean): List<Post>? {
        return FirebaseFirestore.getInstance().collection("posts")
            .let { if (publicOnly) it.whereEqualTo("publicVisibility", true) else it }
            .whereEqualTo("user", user)
            .get()
            .await()
            .map {
                val post = it.toObject(Post::class.java)
                post.id = it.id
                post
            }
            .sortedByDescending { it.date }
    }
}