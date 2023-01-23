package com.mikhnovskiy.spacex.tools

import android.util.Range
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

class DateHelper @Inject constructor() {

    companion object {
        private const val d_MM_YYYY_at_HH_mm_ss_DATE_PATTERN = "d.MM.yyyy 'at' HH:mm:ss"
    }

    private val converterSimpleDateFormatter: SimpleDateFormat = SimpleDateFormat("", Locale.getDefault())
        .apply { this.timeZone = TimeZone.getDefault() }

    fun getDatesUnixRangeByYears(start : Int?, end: Int?) : Range<Long>? {
        return if (start == null && end == null) {
            null
        } else {
            val startStrDate = start ?: end ?: return null
            val endStrDate = end ?: start ?: return null

            val startDate = Calendar.getInstance().apply {
                this.set(Calendar.YEAR, startStrDate)
                this.set(Calendar.MONTH, Calendar.JANUARY)
                this.set(Calendar.DAY_OF_MONTH, this.getActualMinimum(Calendar.DAY_OF_MONTH))
            }.timeInMillis / 1000

            val endDate = Calendar.getInstance().apply {
                this.set(Calendar.YEAR, endStrDate)
                this.set(Calendar.MONTH, Calendar.DECEMBER)
                this.set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))
            }.timeInMillis / 1000
            Range(startDate, endDate)
        }
    }


    fun countDaysDifference(unix: Long) : Int {
        val days = abs(((unix * 1000) - Date().time))
        return TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS).toInt()
    }

    fun convertUnixToStringDate(unix: Long): String {
        val date = Date(unix * 1000)
        return converterSimpleDateFormatter.let {
            it.applyPattern(d_MM_YYYY_at_HH_mm_ss_DATE_PATTERN)
            it.format(date)
        }
    }

}