package com.mikhnovskiy.spacex.presentation.screens

import android.os.Bundle
import com.mikhnovskiy.spacex.core.presentation.BaseActivity
import com.mikhnovskiy.spacex.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun bind() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        activityInjector().inject(this)
        super.onCreate(savedInstanceState)
    }
}