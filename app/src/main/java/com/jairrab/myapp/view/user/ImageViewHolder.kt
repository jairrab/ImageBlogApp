package com.jairrab.myapp.view.user

import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewUserFeedItemBinding
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.showView

class ImageViewHolder(
    private val remoteRepo: RemoteRepo,
    private val binding: ViewUserFeedItemBinding,
    private val callback: (UserAction) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val context get() = binding.root.context
    private lateinit var item: Post

    init {
        binding.publicIv.setOnClickListener {
            item.publicVisibility = !item.publicVisibility
            updatePublicIcon(item)
            callback(UserAction.ChangePublicVisibility(item))
        }
        binding.deleteIv.setOnClickListener {
            callback(UserAction.DeletePost(item))
        }
    }

    suspend fun updateItem(
        isLoggedInUser: Boolean,
        item: Post
    ) {
        this.item = item
        binding.deleteIv.showView(isLoggedInUser)
        binding.publicIv.showView(isLoggedInUser)
        if (isLoggedInUser) updatePublicIcon(item)
        val image = remoteRepo.getImage(item)
        Glide.with(binding.imageView.context)
            .load(image)
            .into(binding.imageView)
    }

    private fun updatePublicIcon(item: Post) {
        binding.publicIv.setColorFilter(
            if (item.publicVisibility) {
                getColor(context, R.color.color_blue_500)
            } else getColor(context, R.color.color_grey_400)
        )
    }

}