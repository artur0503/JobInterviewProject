package com.mikhnovskiy.spacex.data.network.interceptor

import android.accounts.NetworkErrorException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class HeaderModifierInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val requestBuilder = chain.request().newBuilder()
            //add headers

            return chain.proceed(requestBuilder.build())
        } catch (ex: Exception) {
            throw NetworkErrorException(ex.message)
        }
    }
}