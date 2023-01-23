package com.mikhnovskiy.spacex.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.mikhnovskiy.spacex.BundleKeys
import com.mikhnovskiy.spacex.core.presentation.BaseBottomDialogFragment
import com.mikhnovskiy.spacex.databinding.DialogErrorBinding
import com.mikhnovskiy.spacex.tools.ErrorModel

class ErrorBottomDialogFragment : BaseBottomDialogFragment<DialogErrorBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): DialogErrorBinding {
        return DialogErrorBinding.inflate(inflater, container, false)
    }

    override fun contentInflated(binding: DialogErrorBinding, arguments: Bundle?) {
        val errorModel = arguments?.getParcelable<ErrorModel>(BundleKeys.ERROR_MODEL_KEY)
        binding.textViewTitleErrorDialog.text = errorModel?.title
        binding.textViewDescriptionErrorDialog.text = errorModel?.detail
    }

    companion object {
        fun show(fragmentManager: FragmentManager, errorModel: ErrorModel?) = ErrorBottomDialogFragment().run {
            this.arguments = bundleOf(BundleKeys.ERROR_MODEL_KEY to errorModel)
            this.show(fragmentManager)
        }
    }
}