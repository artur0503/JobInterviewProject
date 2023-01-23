package com.mikhnovskiy.spacex.presentation.screens.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mikhnovskiy.spacex.R
import com.mikhnovskiy.spacex.core.presentation.BaseFragment
import com.mikhnovskiy.spacex.databinding.FragmentListingBinding
import com.mikhnovskiy.spacex.presentation.adapters.ListingAdapter
import com.mikhnovskiy.spacex.presentation.dialogs.FilterBottomDialogFragment
import com.mikhnovskiy.spacex.presentation.models.AdapterListItem
import com.mikhnovskiy.spacex.presentation.models.PredicatesModel
import com.mikhnovskiy.spacex.presentation.models.list_items.CompanyInfoModel
import com.mikhnovskiy.spacex.presentation.models.list_items.HeaderModel
import com.mikhnovskiy.spacex.presentation.models.list_items.LaunchModel
import com.mikhnovskiy.spacex.presentation.screens.WebViewActivityArgs
import com.mikhnovskiy.spacex.tools.DefaultItemDecorator
import com.mikhnovskiy.spacex.tools.RvScrollListener
import com.mikhnovskiy.spacex.tools.onLastVisibleItemPos
import javax.inject.Inject


open class ListingFragment : BaseFragment<FragmentListingBinding>() {

    @Inject
    protected lateinit var viewModel: ListingViewModel

    private val headersList: List<AdapterListItem> by lazy {
        listOf(HeaderModel(getString(R.string.company_string)), HeaderModel(getString(R.string.launches_string)))
    }

    private val rvAdapter: ListingAdapter by lazy {
        ListingAdapter(headersList.toMutableList(), ::handleLaunchClick)
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) = FragmentListingBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentInjector().inject(this)
        super.onCreate(savedInstanceState)
        viewModel.run {
            this.progressLiveData.observe(this@ListingFragment, ::handleProgressUpdate)
            this.companyUpdateLiveData.observe(this@ListingFragment, ::handleCompanyUpdate)
            this.itemsListUpdateLiveData.observe(this@ListingFragment, ::handleItemsListUpdate)
            this.itemsListLoadPageLiveData.observe(this@ListingFragment, ::handleItemsListNextPage)
            this.failureLiveData.observe(this@ListingFragment, ::handleError)
            this.updateAllData()
        }
    }

    override fun contentInflated(binder: FragmentListingBinding, arguments: Bundle?) {
        binder.recyclerViewListing.addItemDecoration(DefaultItemDecorator(topSpace = resources.getDimension(R.dimen.list_vertical_decoration_size).toInt()))
        binder.recyclerViewListing.onLastVisibleItemPos(::handleLastVisibleItemPos)
        binder.recyclerViewListing.adapter = rvAdapter

        binder.swipeRefreshLayoutListing.isEnabled = true
        binder.swipeRefreshLayoutListing.setOnRefreshListener { viewModel.updateAllData() }

        binder.toolbarListing.setOnMenuItemClickListener {
            if (it.itemId == R.id.filterMainMenu) {
                FilterBottomDialogFragment.show(childFragmentManager, viewModel.getPredicates(), ::handlePredicates)
            }
            true
        }
    }

    private fun handlePredicates(predicate : PredicatesModel?) {
        viewModel.updateOnlyList(predicate)
    }

    private fun handleLastVisibleItemPos(pos: Int, direction: RvScrollListener.ScrollDirection) {
        if (pos == rvAdapter.itemCount - DEFAULT_LAST_LOADING_DIF && direction == RvScrollListener.ScrollDirection.DOWN) {
            viewModel.loadItemsListNextPage()
        }
    }

    private fun handleLaunchClick(launchModel: LaunchModel) {
        val url = (launchModel.wikipediaUrl ?: launchModel.videoUrl)
        if (url != null) {
            findNavController().navigate(R.id.action_listingFragment_to_webViewActivity, WebViewActivityArgs(url).toBundle())
        } else {
            Toast.makeText(requireContext(), getString(R.string.no_information_string), Toast.LENGTH_SHORT).show()
        }
    }

    override fun handleError(throwable: Throwable) {
        hideProgress()
        if (binder.swipeRefreshLayoutListing.isRefreshing) {
            binder.swipeRefreshLayoutListing.isRefreshing = false
        }
        super.handleError(throwable)
    }

    private fun handleProgressUpdate(inProgress: Boolean?) {
        if (inProgress == true) showProgress() else hideProgress()
        if (binder.swipeRefreshLayoutListing.isRefreshing) {
            binder.swipeRefreshLayoutListing.isRefreshing = false
        }
    }

    private fun handleCompanyUpdate(companyInfoModel: CompanyInfoModel?) {
        if (companyInfoModel != null) {
            rvAdapter.updateCompanyInfo(companyInfoModel)
        }
    }

    private fun handleItemsListUpdate(list: List<LaunchModel>?) {
        if (list != null) {
            rvAdapter.updateItemsList(list)
        }
    }

    private fun handleItemsListNextPage(list: List<LaunchModel>?) {
        if (!list.isNullOrEmpty()) {
            rvAdapter.addItemsList(list)
        }
    }

    companion object {
        private const val DEFAULT_LAST_LOADING_DIF = 10
    }
}