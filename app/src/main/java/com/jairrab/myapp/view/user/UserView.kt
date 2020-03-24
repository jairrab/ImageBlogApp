package com.jairrab.myapp.view.user

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.jairrab.myapp.MyApp
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewUserBinding
import com.jairrab.myapp.models.UserAction.*
import com.jairrab.myapp.repo.LocalRepo
import com.jairrab.myapp.repo.RemoteRepo
import com.jairrab.myapp.utils.*
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserView : Fragment(R.layout.view_user) {

    @Inject lateinit var localRepo: LocalRepo
    @Inject lateinit var remoteRepo: RemoteRepo
    @Inject lateinit var fileUtil: FileUtil
    @Inject lateinit var toaster: Toaster

    private val activityViewModel by activityViewModels<ActivityViewModel>()

    private val binding by viewBinding { ViewUserBinding.bind(it) }
    private val coroutineScope get() = viewLifecycleOwner.lifecycleScope
    private var listAdapter: UserFeedAdapter? = null
    private val pickFileRequest = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.fragmentComponent(requireContext()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleTv.text = activityViewModel.currentUser

        binding.recyclerView.run {
            coroutineScope.launch {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                listAdapter = UserFeedAdapter(coroutineScope, remoteRepo, isLoggedInUser()) {
                    coroutineScope.launch {
                        when (it) {
                            is ChangePublicVisibility -> activityViewModel.updateData(it.post)
                            is DeletePost             -> activityViewModel.deleteData(it.post)
                            is ViewImage              -> {
                                activityViewModel.currentPost = it.post
                                findNavController().popBackStack(R.id.userView, true)
                                findNavController().navigate(R.id.postView)
                            }
                        }
                        updateFeed()
                    }
                }
                adapter = listAdapter
                updateFeed()
            }
        }

        binding.postBn.run {
            showView(!isAnonymous)
            setOnClickListener {
                if (binding.progressCircular.visibility == VISIBLE) {
                    toaster.showToast("Please wait for task to complete...")
                    return@setOnClickListener
                }
                binding.progressCircular.showView(true)
                fileUtil.pickFile(
                    fragment = this@UserView,
                    requestCode = pickFileRequest,
                    mimeType = MimeType.IMAGES
                )
            }
        }

        binding.homeBn.setOnClickListener {
            findNavController().navigate(R.id.homeView)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickFileRequest && resultCode == RESULT_OK) {
            coroutineScope.launch { activityViewModel.saveData(data?.data) }
        }
    }

    private suspend fun updateFeed() {
        activityViewModel.currentUser?.let { user ->
            binding.progressCircular.showView(true)

            withContext(Dispatchers.Default) { localRepo.getData(user, isAnonymous) }?.let { data ->
                listAdapter?.isLoggedInUser = isLoggedInUser()
                listAdapter?.submitList(data)
            }

            remoteRepo.getData(user, isAnonymous)?.let { data ->
                binding.progressCircular.showView(false)
                listAdapter?.isLoggedInUser = isLoggedInUser()
                listAdapter?.submitList(data)
            } ?: binding.progressCircular.showView(false)
        }
    }

    private fun isLoggedInUser(): Boolean {
        return activityViewModel.loggedInUser == activityViewModel.currentUser
               && !isAnonymous
    }

    private val isAnonymous
        get() = FirebaseAuth.getInstance().currentUser?.isAnonymous == true
}