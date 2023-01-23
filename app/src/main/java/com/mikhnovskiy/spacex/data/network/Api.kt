package com.mikhnovskiy.spacex.data.network

import com.mikhnovskiy.spacex.data.network.models.company.CompanyInfoResponse
import com.mikhnovskiy.spacex.data.network.models.launches.LaunchesResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("company")
    fun getCompanyInfo() : Single<CompanyInfoResponse>

    @POST("launches/query")
    fun getLaunches(@Body body: LinkedHashMap<String, Any?>) : Single<LaunchesResponse>

}