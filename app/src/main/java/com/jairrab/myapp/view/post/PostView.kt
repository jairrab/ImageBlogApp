package com.jairrab.myapp.view.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.jairrab.myapp.MyApp
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewHomeBinding
import com.jairrab.myapp.repo.LocalRepo
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.Toaster
import com.jairrab.myapp.utils.viewBinding
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModel
import javax.inject.Inject

class PostView : Fragment(R.layout.view_home) {

    @Inject lateinit var localRepo: LocalRepo
    @Inject lateinit var remoteRepo: RemoteRepo
    @Inject lateinit var toaster: Toaster

    private val activityViewModel by activityViewModels<ActivityViewModel>()

    private val coroutineScope get() = viewLifecycleOwner.lifecycleScope
    private val binding by viewBinding { ViewHomeBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.fragmentComponent(requireContext()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}