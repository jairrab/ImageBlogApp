package com.jairrab.myapp.view.home

import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jairrab.myapp.databinding.ViewFeedItemBinding
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.models.UserAction
import com.jairrab.myapp.repo.RemoteRepo

class ImageViewHolder(
    private val colors: Array<Int>,
    private val remoteRepo: RemoteRepo,
    private val binding: ViewFeedItemBinding,
    private val callback: (UserAction) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val context get() = binding.root.context
    private var item: Post? = null

    init {
        binding.userTv.setOnClickListener {
            item?.let { callback(UserAction.ViewUser(it)) }
        }
        binding.imageView.setOnClickListener {
            item?.let { callback(UserAction.ViewImage(it)) }
        }
    }

    suspend fun updateItem(item: Post) {
        this.item = item
        binding.userTv.text = item.user
        val color = getColor(context, colors[getStringNum(item.user!!)])
        binding.userTv.setBackgroundColor(color)
        val image = remoteRepo.getImage(item)
        Glide.with(binding.imageView.context)
            .load(image)
            .into(binding.imageView)
    }

    //utility to assign a number given a string
    private fun getStringNum(s: String): Int {
        var score = 0
        for (element in s) score += element - 'a' + 1
        return score % 9
    }
}