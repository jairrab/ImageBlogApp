package com.jairrab.myapp.view.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.jairrab.myapp.MyApp
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ViewLoginBinding
import com.jairrab.myapp.utils.viewBinding
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModel

class LoginView : Fragment(R.layout.view_login) {

    private val activityViewModel by activityViewModels<ActivityViewModel>()
    private val binding by viewBinding { ViewLoginBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.fragmentComponent(requireContext()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBn.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                123
            )
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null)
        FirebaseAuth.getInstance().currentUser?.let {
            signInUser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
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