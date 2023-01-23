package com.mikhnovskiy.spacex.data.repository

import androidx.annotation.WorkerThread
import com.mikhnovskiy.spacex.data.network.Api
import com.mikhnovskiy.spacex.data.network.models.launches.LaunchesRequest
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val api: Api) {

    @WorkerThread
    fun getCompanyInfo() = api.getCompanyInfo()

    @WorkerThread
    fun loadLaunchesPages(launchesRequest: LaunchesRequest) = api.getLaunches(launchesRequest.requestBody)

}