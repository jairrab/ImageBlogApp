package com.jairrab.myapp.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.jairrab.myapp.MyApp
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewLoginBinding
import com.jairrab.myapp.utils.Toaster
import com.jairrab.myapp.utils.showView
import com.jairrab.myapp.utils.viewBinding
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModel
import javax.inject.Inject

class LoginView : Fragment(R.layout.view_login) {

    @Inject lateinit var toaster: Toaster

    private val activityViewModel by activityViewModels<ActivityViewModel>()
    private val binding by viewBinding { ViewLoginBinding.bind(it) }
    private val loginRequest = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.fragmentComponent(requireContext()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBn.setOnClickListener {
            binding.progressCircular.showView(true)
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(arrayListOf(GoogleBuilder().build()))
                    .build(),
                loginRequest
            )
        }

        binding.useAppAnonymouslyBn.setOnClickListener {
            binding.progressCircular.showView(true)
            FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener { task ->
                    binding.progressCircular.showView(false)
                    if (task.isSuccessful) {
                        signInUser()
                    } else {
                        toaster.showToast("Failed to login anonymously")
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null)
        FirebaseAuth.getInstance().currentUser?.let {
            val user = FirebaseAuth.getInstance().currentUser
            if (user?.isAnonymous == true) return
            signInUser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == loginRequest) {
            binding.progressCircular.showView(true)
            if (resultCode == Activity.RESULT_OK) {
                signInUser()
            } else {
                val response = IdpResponse.fromResultIntent(data)
                if (response != null) throw response.error!!
            }
        }
    }

    private fun signInUser() {
        val user = FirebaseAuth.getInstance().currentUser
        activityViewModel.loggedInUser = user?.email
        activityViewModel.currentUser = user?.email
        findNavController().popBackStack(R.id.loginView, true)
        findNavController().navigate(R.id.homeView)
    }
}