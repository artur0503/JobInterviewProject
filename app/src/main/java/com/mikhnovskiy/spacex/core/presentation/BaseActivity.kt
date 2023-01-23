package com.mikhnovskiy.spacex.core.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mikhnovskiy.spacex.SpaceXApp
import com.mikhnovskiy.spacex.presentation.dialogs.ProgressDialogFragment

abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity() {

    val binder: Binding by lazy(LazyThreadSafetyMode.NONE) { bind() }

    abstract fun bind(): Binding

    fun activityInjector() = (application as SpaceXApp).injector.activityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
    }

    fun showProgress() {
        hideProgress()
        ProgressDialogFragment.show(supportFragmentManager)
    }

    fun hideProgress() {
        val dialogProgress = supportFragmentManager.findFragmentByTag(ProgressDialogFragment::class.java.simpleName)
        if (dialogProgress != null && dialogProgress is ProgressDialogFragment) {
            dialogProgress.dismiss()
        }
    }
}