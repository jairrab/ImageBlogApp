package com.jairrab.myapp.view.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jairrab.myapp.databinding.ViewUserFeedItemBinding
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.repo.RemoteRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserFeedAdapter(
    private val coroutineScope: CoroutineScope,
    private val remoteRepo: RemoteRepo,
    var isLoggedInUser: Boolean,
    private val callback: (UserAction) -> Unit
) : ListAdapter<Post, ImageViewHolder>(A) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ViewUserFeedItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(remoteRepo, binding, callback)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        coroutineScope.launch { holder.updateItem(isLoggedInUser, getItem(position)) }
    }

    companion object A : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}

