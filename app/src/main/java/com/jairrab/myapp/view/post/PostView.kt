package com.jairrab.myapp.view.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.jairrab.myapp.MyApp
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewPostBinding
import com.jairrab.myapp.repo.LocalRepo
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.TimeUtils
import com.jairrab.myapp.utils.Toaster
import com.jairrab.myapp.utils.viewBinding
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostView : Fragment(R.layout.view_post) {

    @Inject lateinit var localRepo: LocalRepo
    @Inject lateinit var remoteRepo: RemoteRepo
    @Inject lateinit var toaster: Toaster
    @Inject lateinit var timeUtils: TimeUtils

    private val activityViewModel by activityViewModels<ActivityViewModel>()

    private val coroutineScope get() = viewLifecycleOwner.lifecycleScope
    private val binding by viewBinding { ViewPostBinding.bind(it) }
    private val currentPost get() = activityViewModel.currentPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.fragmentComponent(requireContext()).inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userTv.run {
            text = currentPost?.user
            activityViewModel.currentUser = currentPost?.user
            setOnClickListener { findNavController().navigate(R.id.userView) }
        }

        binding.dateTv.run {
            val date = timeUtils.getDate(currentPost?.date)
            val time = timeUtils.getTime(currentPost?.date)
            text = "$date $time"
            activityViewModel.currentUser = currentPost?.user
            setOnClickListener { findNavController().navigate(R.id.userView) }
        }

        coroutineScope.launch {
            currentPost?.let {
                val image = remoteRepo.getImage(it)
                Glide.with(binding.postIv.context)
                    .load(image)
                    .into(binding.postIv)
            }
        }
    }
}