package com.jairrab.myapp.view.home

import android.graphics.drawable.Drawable
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jairrab.myapp.databinding.ViewFeedItemBinding
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.models.UserAction
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.showView

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
        loadImage(item)
    }

    private suspend fun loadImage(item: Post) {
        binding.progressBar.showView(true)
        binding.imageView.visibility = INVISIBLE
        val image = remoteRepo.getImage(item)
        Glide.with(binding.imageView.context)
            .load(image)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.showView(false)
                    binding.imageView.visibility = VISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.showView(false)
                    binding.imageView.visibility = VISIBLE
                    return false
                }

            })
            .into(binding.imageView)
    }

    //utility to assign a number given a string
    private fun getStringNum(s: String): Int {
        var score = 0
        for (element in s) score += element - 'a' + 1
        return score % 9
    }
}