/* Created 3/25/2020 7:33 AM */

package com.jairrab.myapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TimeUtils @Inject constructor() {

    private val datePattern = (DateFormat.getDateInstance(DateFormat.LONG)
            as SimpleDateFormat).toLocalizedPattern()

    private val timePattern = (DateFormat.getTimeInstance(DateFormat.SHORT)
            as SimpleDateFormat).toLocalizedPattern()

    private val dateFormatter = SimpleDateFormat(datePattern, Locale.getDefault())

    private val timeFormatter = SimpleDateFormat(timePattern, Locale.getDefault())

    fun getDate(timeInMills: Long?, pattern: String = datePattern): String? {
        return timeInMills?.let {
            try {
                dateFormatter.applyPattern(pattern)
                dateFormatter.format(Date(timeInMills))
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    fun getTime(timeInMills: Long?, pattern: String = timePattern): String? {
        return timeInMills?.let {
            try {
                timeFormatter.applyPattern(pattern)
                timeFormatter.format(Date(timeInMills))
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}