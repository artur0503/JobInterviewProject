package com.mikhnovskiy.spacex.core.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mikhnovskiy.spacex.R
import com.mikhnovskiy.spacex.SpaceXApp

abstract class BaseBottomDialogFragment<Binding : ViewBinding> : BottomSheetDialogFragment() {

    private lateinit var binder: Binding
    private var isSilenceDismiss: Boolean = false
    private var onDismissCallback: () -> Unit = {}

    abstract fun bind(inflater: LayoutInflater, container: ViewGroup?): Binding
    abstract fun contentInflated(binding: Binding, arguments: Bundle?)

    fun fragmentInjector() = (requireActivity().application as SpaceXApp).injector.fragmentComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = bind(inflater, container)
        return binder.root
    }

    override fun getTheme(): Int {
        return R.style.TransparentDialogTheme_BottomSheet
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (!isSilenceDismiss) {
            onDismissCallback.invoke()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentInflated(binder, arguments)
        dialog?.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
    }

    fun activityInjector() = (requireActivity().application as SpaceXApp).injector.fragmentComponent

    fun silenceDismiss() {
        isSilenceDismiss = true
        dismiss()
    }

    fun onDismissCallback(callback: () -> Unit) {
        onDismissCallback = callback
    }

    open fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, BaseBottomDialogFragment::class.java.simpleName)
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)
        behavior.isFitToContents = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}