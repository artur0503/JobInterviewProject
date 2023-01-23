package com.mikhnovskiy.spacex.presentation.screens.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mikhnovskiy.spacex.core.presentation.BaseViewModel
import com.mikhnovskiy.spacex.domain.ListingLoaderUseCase
import com.mikhnovskiy.spacex.presentation.models.PredicatesModel
import com.mikhnovskiy.spacex.presentation.models.list_items.CompanyInfoModel
import com.mikhnovskiy.spacex.presentation.models.list_items.LaunchModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListingViewModel @Inject constructor(
    private val listingLoaderUseCase: ListingLoaderUseCase
) : BaseViewModel() {

    private var predicatesModel: PredicatesModel? = null
    private val progressMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val companyUpdateMutableLiveData: MutableLiveData<CompanyInfoModel> = MutableLiveData()
    private val loadPageMutableLiveData: MutableLiveData<List<LaunchModel>> = MutableLiveData()
    private val itemsListUpdateMutableLiveData: MutableLiveData<List<LaunchModel>> = MutableLiveData()

    val progressLiveData: LiveData<Boolean>
        get() = progressMutableLiveData

    val companyUpdateLiveData: LiveData<CompanyInfoModel>
        get() = companyUpdateMutableLiveData

    val itemsListLoadPageLiveData: LiveData<List<LaunchModel>>
        get() = loadPageMutableLiveData

    val itemsListUpdateLiveData: LiveData<List<LaunchModel>>
        get() = itemsListUpdateMutableLiveData

    fun getPredicates() = predicatesModel

    fun updateAllData() {
        val single = listingLoaderUseCase.firstLoadOrRefresh()
            .doOnSubscribe { controlProgress(true) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { controlProgress(false) }
            .subscribe(
                {
                    companyUpdateMutableLiveData.value = it.second
                    itemsListUpdateMutableLiveData.value = it.first
                }, failureMutableLiveData::setValue
            )

        subjectList.add(single)
    }

    fun updateOnlyList(predicatesModel: PredicatesModel? = null) {
        val single = listingLoaderUseCase.refreshList(predicatesModel)
            ?.doOnSubscribe {
                this.predicatesModel = predicatesModel
                controlProgress(true)
            }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doFinally { controlProgress(false) }
            ?.subscribe(itemsListUpdateMutableLiveData::setValue, failureMutableLiveData::setValue)

        single?.let(subjectList::add)
    }

    fun loadItemsListNextPage() {
        val single = listingLoaderUseCase
            .loadNextPage()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(loadPageMutableLiveData::setValue, failureMutableLiveData::setValue)

        single?.let(subjectList::add)
    }

    private fun controlProgress(inProgress: Boolean) = progressMutableLiveData.postValue(inProgress)
}