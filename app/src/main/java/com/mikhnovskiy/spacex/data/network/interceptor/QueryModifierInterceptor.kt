package com.mikhnovskiy.spacex.data.network.interceptor

import android.accounts.NetworkErrorException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class QueryModifierInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request: Request = chain.request()
            val url = request.url.newBuilder().apply {
                //add params for query
            }.build()
            val newRequest = request.newBuilder().url(url).build()
            return chain.proceed(newRequest)
        } catch (ex: Exception) {
            throw NetworkErrorException(ex.message)
        }
    }
}