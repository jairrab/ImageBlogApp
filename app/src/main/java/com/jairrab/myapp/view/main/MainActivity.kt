package com.jairrab.myapp.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jairrab.myapp.MyApp
import com.jairrab.myapp.R
import com.jairrab.myapp.databinding.ActivityMainBinding
import com.jairrab.myapp.view.viewmodelfactory.ViewModelFactory
import com.jairrab.myapp.utils.makeStatusBarTransparent
import com.jairrab.myapp.view.main.viewmodel.ActivityViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val activityViewModel by viewModels<ActivityViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.activityComponent(this).inject(this)
        makeStatusBarTransparent()

        activityViewModel.start()
    }
}
