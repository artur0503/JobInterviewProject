package com.mikhnovskiy.spacex.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.mikhnovskiy.spacex.R
import com.mikhnovskiy.spacex.core.adapters.BaseAdapter
import com.mikhnovskiy.spacex.core.adapters.BaseViewHolder
import com.mikhnovskiy.spacex.databinding.ItemCompanyInfoBinding
import com.mikhnovskiy.spacex.databinding.ItemHeaderBinding
import com.mikhnovskiy.spacex.databinding.ItemLaunchInfoBinding
import com.mikhnovskiy.spacex.presentation.models.AdapterListItem
import com.mikhnovskiy.spacex.presentation.models.list_items.CompanyInfoModel
import com.mikhnovskiy.spacex.presentation.models.list_items.HeaderModel
import com.mikhnovskiy.spacex.presentation.models.list_items.LaunchModel
import com.mikhnovskiy.spacex.tools.onSingleClick

class ListingAdapter(
    override val baseTypeList: MutableList<AdapterListItem>,
    private val callback: (LaunchModel) -> Unit
) : BaseAdapter<AdapterListItem>() {

    fun updateCompanyInfo(companyInfoModel: CompanyInfoModel) {
        val oldModel = baseTypeList.find { it is CompanyInfoModel }
        val pos = baseTypeList.indexOf(oldModel).takeIf { it >= 0 } ?: 1

        if (companyInfoModel != oldModel) {
            if (oldModel != null) {
                baseTypeList[pos] = companyInfoModel
                notifyItemChanged(pos)
            } else {
                baseTypeList.add(pos, companyInfoModel)
                notifyItemInserted(pos)
            }
        }
    }

    fun updateItemsList(list: List<AdapterListItem>) {
        val firstPos = baseTypeList.indexOfFirst { it is LaunchModel }
        val firstSize = baseTypeList.size
        baseTypeList.removeIf { it is LaunchModel }
        baseTypeList.addAll(list)
        if (baseTypeList.size < firstSize) {
            notifyItemRangeRemoved(baseTypeList.lastIndex, firstSize - baseTypeList.size)
        }
        notifyItemRangeChanged(firstPos, list.size)
    }

    fun addItemsList(list: List<AdapterListItem>) {
        val lastPos = baseTypeList.lastIndex + 1
        baseTypeList.addAll(list)
        notifyItemRangeInserted(lastPos, list.size)
    }

    override fun getItemViewType(position: Int) = baseTypeList[position].getType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<out AdapterListItem> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CompanyInfoModel.COMPANY_TYPE -> CompanyViewHolder(ItemCompanyInfoBinding.inflate(layoutInflater, parent, false))
            LaunchModel.LAUNCH_TYPE -> LaunchViewHolder(ItemLaunchInfoBinding.inflate(layoutInflater, parent, false))
            HeaderModel.HEADER_TYPE -> HeaderViewHolder(ItemHeaderBinding.inflate(layoutInflater, parent, false))
            else -> throw Exception("Wrong viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out AdapterListItem>, position: Int) {
        val type = getItemViewType(position)
        val data = baseTypeList[position]
        when (type) {
            CompanyInfoModel.COMPANY_TYPE -> {
                castViewHolder<CompanyViewHolder>(holder).bind(data as CompanyInfoModel, position)
            }
            LaunchModel.LAUNCH_TYPE -> {
                castViewHolder<LaunchViewHolder>(holder).bind(data as LaunchModel, position)
            }
            HeaderModel.HEADER_TYPE -> {
                castViewHolder<HeaderViewHolder>(holder).bind(data as HeaderModel, position)
            }
        }
    }

    private inner class HeaderViewHolder(private val binding: ItemHeaderBinding) : BaseViewHolder.MutableViewHolder<ItemHeaderBinding, HeaderModel>(binding) {
        override fun bind(data: HeaderModel, pos: Int) {
            binding.textViewHeader.text = data.title
        }

    }

    private inner class CompanyViewHolder(private val binding: ItemCompanyInfoBinding) : BaseViewHolder.MutableViewHolder<ItemCompanyInfoBinding, CompanyInfoModel>(binding) {
        override fun bind(data: CompanyInfoModel, pos: Int) {
            binding.textViewCompany.run {
                this.text = String.format(
                    context.getString(R.string.company_description_text),
                    data.name,
                    data.founderName,
                    data.foundationYear,
                    data.employeesCount,
                    data.launchSites,
                    data.price
                )
            }
        }

    }

    private inner class LaunchViewHolder(private val binding: ItemLaunchInfoBinding) : BaseViewHolder.MutableViewHolder<ItemLaunchInfoBinding, LaunchModel>(binding) {
        override fun bind(data: LaunchModel, pos: Int) {
            val context = binding.root.context
            val wordForDays = if (data.upcoming == true) context.getString(R.string.from_string) else context.getString(R.string.since_string)
            val statusImage = if (data.isSuccess == true) R.drawable.ic_round_done_outline_24 else if (data.isSuccess == false) R.drawable.ic_baseline_close_24 else null
            val statusDrawable = statusImage?.let { ContextCompat.getDrawable(context, it) }

            binding.root.onSingleClick {
                callback.invoke(data)
            }

            Glide.with(binding.root)
                .load(data.imgUrl)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(binding.imageViewLaunch)

            binding.textViewNameLaunch.text = data.missionName
            binding.textViewDateTimeLaunch.text = data.missionDateTime
            binding.textViewRocketLaunch.text = data.rockets
            binding.textViewDaysLaunch.text = data.daysDifference.toString()
            binding.textViewLabelDaysLaunch.text = String.format(context.getString(R.string.days_count_mask), wordForDays)
            binding.imageViewIsSuccess.setImageDrawable(statusDrawable)
            binding.imageViewIsSuccess.isVisible = data.upcoming != true


        }

    }

}