package com.mikhnovskiy.spacex.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mikhnovskiy.spacex.SpaceXApp
import com.mikhnovskiy.spacex.R
import com.mikhnovskiy.spacex.presentation.dialogs.ErrorBottomDialogFragment
import com.mikhnovskiy.spacex.tools.ErrorModel
import com.mikhnovskiy.spacex.tools.HttpCallFailureException
import com.mikhnovskiy.spacex.tools.NoNetworkException

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    lateinit var binder: Binding

    abstract fun bind(inflater: LayoutInflater, container: ViewGroup?): Binding
    abstract fun contentInflated(binder: Binding, arguments: Bundle?)

    fun fragmentInjector() = (requireActivity().application as SpaceXApp).injector.fragmentComponent

    fun showProgress() {
        if (requireActivity() is BaseActivity<*>) {
            (requireActivity() as BaseActivity<*>).showProgress()
        }
    }

    fun hideProgress() {
        if (requireActivity() is BaseActivity<*>) {
            (requireActivity() as BaseActivity<*>).hideProgress()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = bind(inflater, container)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentInflated(binder, arguments)
    }

    open fun handleError(throwable: Throwable) {
        val errorModel = when (throwable) {
            is HttpCallFailureException -> throwable.errorModel
            is NoNetworkException -> ErrorModel(
                title = getString(R.string.no_network_connection_string),
                detail = getString(R.string.check_network_connection_string)
            )
            else -> ErrorModel(
                title = getString(R.string.oops_something_wrong_string),
                detail = getString(R.string.try_again_latter_string)
            )
        }
        ErrorBottomDialogFragment.show(childFragmentManager, errorModel)
    }
}