package com.mikhnovskiy.spacex.tools

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

@Parcelize
data class ErrorModel(
    val detail: String?,
    val title: String?,
    val status: Int? = null,
    val errorDetails: List<String>? = null
) : Parcelable

class NoNetworkException : IOException()
class HttpCallFailureException(error: HttpException) : RuntimeException(error) {
    val errorModel: ErrorModel? = convertError(error.response()?.errorBody())

    private fun convertError(response: ResponseBody?): ErrorModel? {
        val errorString = response?.string()
        val gson = Gson()
        return errorString?.let { gson.fromJson(it, ErrorModel::class.java) }
    }
}

