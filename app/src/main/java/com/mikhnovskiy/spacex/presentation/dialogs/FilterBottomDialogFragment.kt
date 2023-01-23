package com.mikhnovskiy.spacex.presentation.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import com.mikhnovskiy.spacex.BundleKeys
import com.mikhnovskiy.spacex.R
import com.mikhnovskiy.spacex.SortTypes
import com.mikhnovskiy.spacex.core.presentation.BaseBottomDialogFragment
import com.mikhnovskiy.spacex.databinding.DialogFilterBinding
import com.mikhnovskiy.spacex.presentation.models.PredicatesModel

class FilterBottomDialogFragment : BaseBottomDialogFragment<DialogFilterBinding>() {

    private var predicates: PredicatesModel? = null
    private var callback: ((PredicatesModel?) -> Unit)? = null

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): DialogFilterBinding {
        return DialogFilterBinding.inflate(inflater, container, false)
    }

    override fun contentInflated(binding: DialogFilterBinding, arguments: Bundle?) = binding.run {
        predicates = arguments?.getParcelable<PredicatesModel>(BundleKeys.FILTER_DATA_KEY)?.copy() ?: PredicatesModel()

        editTextBeginYearsFilter.doAfterTextChanged {
            predicates?.yearStart = it.toString().toIntOrNull()
        }

        editTextEndYearsFilter.doAfterTextChanged {
            predicates?.yearEnd = it.toString().toIntOrNull()
        }

        checkboxUpcomingFilter.setOnCheckedChangeListener { _, isChecked ->
            predicates?.isUpcoming = isChecked.takeIf { it }
            controlRadioGroupState(this, !isChecked)
        }

        radioGroupSuccessFilter.setOnCheckedChangeListener { _, id ->
            predicates?.isSuccess = when (id) {
                R.id.radioButtonSuccessFilter -> true
                R.id.radioButtonFailedFilter -> false
                else -> null
            }
        }

        toggleButtonSortFilter.setOnCheckedChangeListener { _, isChecked ->
            predicates?.sortType = if (isChecked) SortTypes.DESC else SortTypes.ASC
        }

        toggleButtonSortFilter.isChecked = predicates?.sortType == SortTypes.DESC
        editTextBeginYearsFilter.setText(predicates?.yearStart?.toString())
        editTextEndYearsFilter.setText(predicates?.yearEnd?.toString())
        checkboxUpcomingFilter.isChecked = predicates?.isUpcoming ?: false
        radioGroupSuccessFilter.check(
            when (predicates?.isSuccess) {
                null -> R.id.radioButtonNoneFilter
                true -> R.id.radioButtonSuccessFilter
                else -> R.id.radioButtonFailedFilter
            }
        )

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback?.invoke(predicates)
    }

    private fun controlRadioGroupState(binding: DialogFilterBinding, isEnable: Boolean) = binding.run {
        if (!isEnable) radioButtonNoneFilter.isChecked = true
        radioGroupSuccessFilter.alpha = if(!isEnable) 0.5f else 1f
        for (i in 0 until radioGroupSuccessFilter.childCount) {
            radioGroupSuccessFilter.getChildAt(i)?.isEnabled = isEnable
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, predicatesModel: PredicatesModel? = null, callback: (PredicatesModel?) -> Unit) = FilterBottomDialogFragment().run {
            this.arguments = bundleOf(BundleKeys.FILTER_DATA_KEY to predicatesModel)
            this.callback = callback
            this.show(fragmentManager)
        }
    }
}