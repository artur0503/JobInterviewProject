package com.mikhnovskiy.spacex.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mikhnovskiy.spacex.R
import com.mikhnovskiy.spacex.databinding.DialogProgressBinding

class ProgressDialogFragment : DialogFragment() {

    private lateinit var binder: DialogProgressBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binder = DialogProgressBinding.inflate(inflater, container, false)
        isCancelable = false
        return binder.root
    }

    override fun getTheme(): Int {
        return R.style.TransparentDialogTheme
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            return ProgressDialogFragment().show(fragmentManager, ProgressDialogFragment::class.java.simpleName)
        }
    }

}