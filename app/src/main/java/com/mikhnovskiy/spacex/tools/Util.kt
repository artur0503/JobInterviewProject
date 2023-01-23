package com.mikhnovskiy.spacex.tools

import android.accounts.NetworkErrorException
import io.reactivex.Single
import retrofit2.HttpException

fun <R> mapErrors() = { single: Single<R> ->
    single.onErrorResumeNext { error ->
        when (error) {
            is HttpException -> Single.error(HttpCallFailureException(error))
            is NetworkErrorException -> Single.error(NoNetworkException())
            else -> Single.error(error)
        }
    }
}