package com.jairrab.myapp.view.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.jairrab.myapp.MyApp
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewHomeBinding
import com.jairrab.myapp.models.Post
import com.jairrab.myapp.repo.LocalRepo
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.*
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeView : Fragment(R.layout.view_home) {

    @Inject lateinit var localRepo: LocalRepo
    @Inject lateinit var remoteRepo: RemoteRepo
    @Inject lateinit var fileUtil: FileUtil
    @Inject lateinit var uriUtil: UriUtil
    @Inject lateinit var toaster: Toaster

    private val activityViewModel by activityViewModels<ActivityViewModel>()

    private val coroutineScope get() = viewLifecycleOwner.lifecycleScope
    private val binding by viewBinding { ViewHomeBinding.bind(it) }
    private var listAdapter: HomeFeedAdapter? = null
    private val pickFileRequest = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.fragmentComponent(requireContext()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.run {
            coroutineScope.launch {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                listAdapter = HomeFeedAdapter(coroutineScope, remoteRepo) {
                    activityViewModel.currentUser = it.user
                    findNavController().navigate(R.id.userView)
                }
                adapter = listAdapter
                updateFeed()
            }
        }

        binding.postBn.run {
            showView(FirebaseAuth.getInstance().currentUser?.isAnonymous == false)
            setOnClickListener {
                if (binding.progressCircular.visibility == View.VISIBLE) {
                    toaster.showToast("Please wait for task to complete...")
                    return@setOnClickListener
                }
                binding.progressCircular.showView(true)
                fileUtil.pickFile(
                    fragment = this@HomeView,
                    requestCode = pickFileRequest,
                    mimeType = MimeType.IMAGES
                )
            }
        }

        binding.logoutBn.setOnClickListener {
            toaster.showToast("Logging out...")
            FirebaseAuth.getInstance().signOut()
            context?.cacheDir?.deleteRecursively()
            findNavController().popBackStack(R.id.homeView, true)
            findNavController().navigate(R.id.loginView)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickFileRequest) {
            if (resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    val uri = data?.data
                    activityViewModel.saveData(uri)
                    cacheUri(uri)
                    updateFeed()
                }
            } else binding.progressCircular.showView(false)
        }
    }

    private suspend fun cacheUri(uri: Uri?) {
        uriUtil.copyUriToFile(
            uri = uri,
            file = fileUtil.getCacheFile(
                Post(
                    user = activityViewModel.currentUser,
                    postName = uri?.lastPathSegment
                )
            )
        )
    }

    private suspend fun updateFeed() {
        binding.progressCircular.showView(true)
        withContext(Dispatchers.Default) { localRepo.getData(true) }?.let { data ->
            listAdapter?.submitList(data)
        }

        remoteRepo.getData(true)?.let { data ->
            binding.progressCircular.showView(false)
            listAdapter?.submitList(data)
            cacheData(data)
        }
    }

    private suspend fun cacheData(data: List<Post>) {
        withContext(Dispatchers.Default) {
            data.forEach {
                localRepo.saveData(it)
            }
        }
    }
}