package com.jairrab.myapp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewFeedItemBinding
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.models.UserAction
import com.jairrab.myapp.repo.RemoteRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeFeedAdapter(
    private val coroutineScope: CoroutineScope,
    private val remoteRepo: RemoteRepo,
    private val callback: (UserAction) -> Unit
) : ListAdapter<Post, ImageViewHolder>(A) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ViewFeedItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(colors, remoteRepo, binding, callback)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        coroutineScope.launch { holder.updateItem(getItem(position)) }
    }

    companion object A : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    private val colors = arrayOf(
        R.color.color_blue_500,
        R.color.color_red_500,
        R.color.color_orange_500,
        R.color.color_cyan_500,
        R.color.color_deeppurple_500,
        R.color.color_teal_500,
        R.color.color_amber_500,
        R.color.color_bluegrey_500,
        R.color.color_brown_500
    )
}

