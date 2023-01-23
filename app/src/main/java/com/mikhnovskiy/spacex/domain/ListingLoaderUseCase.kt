package com.mikhnovskiy.spacex.domain

import com.mikhnovskiy.spacex.data.network.ApiConst
import com.mikhnovskiy.spacex.data.network.models.launches.LaunchesRequest
import com.mikhnovskiy.spacex.data.network.models.launches.RocketItemResponse
import com.mikhnovskiy.spacex.data.repository.NetworkRepository
import com.mikhnovskiy.spacex.presentation.models.PredicatesModel
import com.mikhnovskiy.spacex.presentation.models.list_items.CompanyInfoModel
import com.mikhnovskiy.spacex.presentation.models.list_items.LaunchModel
import com.mikhnovskiy.spacex.tools.DateHelper
import com.mikhnovskiy.spacex.tools.mapErrors
import io.reactivex.Single
import javax.inject.Inject

class ListingLoaderUseCase @Inject constructor(
    private val repository: NetworkRepository,
    private val dateHelper: DateHelper
) {

    private var nextPage: Int? = DEFAULT_PAGE
    private var predicatesModel: PredicatesModel? = null

    fun firstLoadOrRefresh(): Single<Pair<List<LaunchModel>, CompanyInfoModel>> {
        return loadPage(DEFAULT_PAGE).zipWith(loadCompanyInfo(), ::Pair)
    }

    fun refreshList(predicatesModel: PredicatesModel? = null): Single<List<LaunchModel>>? {
        return if (predicatesModel != this.predicatesModel) {
            this.predicatesModel = predicatesModel
            loadPage(DEFAULT_PAGE)
        } else null
    }

    fun loadNextPage() = nextPage?.let(::loadPage)

    private fun loadCompanyInfo() = repository.getCompanyInfo()
        .compose(mapErrors())
        .map { response ->
            CompanyInfoModel(
                name = response.name.toString(),
                founderName = response.founder.toString(),
                foundationYear = response.founded ?: 0,
                employeesCount = response.employees ?: 0,
                price = response.valuation.toString(),
                launchSites = response.launchSites
            )
        }

    private fun loadPage(page: Int): Single<List<LaunchModel>> {
        val filterMap = LaunchesRequest.getFilterMap(
            isSuccess = predicatesModel?.isSuccess,
            isUpcoming = predicatesModel?.isUpcoming,
            dateRange = dateHelper.getDatesUnixRangeByYears(predicatesModel?.yearStart, predicatesModel?.yearEnd)
        )

        val sortMap = LaunchesRequest.getSortMap(predicatesModel?.sortType ?: ApiConst.SortTypes.ASC)

        val request = LaunchesRequest(
            page = page,
            limit = LIMIT,
            filterMap = filterMap,
            sortMap = sortMap,
        )
        return repository.loadLaunchesPages(request)
            .compose(mapErrors())
            .doOnSuccess {
                this.nextPage = it.nextPage
            }
            .map { response ->
                response.docs.map {
                    val dateAndTime = dateHelper.convertUnixToStringDate(it.dateUnix ?: 0)
                    val daysDifference = dateHelper.countDaysDifference(it.dateUnix ?: 0)
                    val rockets = getRocketsString(it.payloads)
                    LaunchModel(
                        missionName = it.name.toString(),
                        missionDateTime = dateAndTime,
                        isSuccess = it.success,
                        wikipediaUrl = it.links?.wikipedia,
                        imgUrl = it.links?.patch?.small,
                        videoUrl = it.links?.webcast,
                        rockets = rockets,
                        upcoming = it.upcoming,
                        daysDifference = daysDifference
                    )
                }
            }
    }

    private fun getRocketsString(rockets: List<RocketItemResponse>?): String {
        val stringBuilder = StringBuilder()
        rockets?.forEachIndexed { i, item ->
            stringBuilder.append(item.toString())
            if (i < rockets.lastIndex) {
                stringBuilder.append("\n")
            }
        }
        return stringBuilder.toString()
    }

    companion object {
        private const val DEFAULT_PAGE = 1
        private const val LIMIT = 20
    }
}