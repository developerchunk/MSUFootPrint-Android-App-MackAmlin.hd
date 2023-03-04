package com.example.msufpnotificationapp.util

import java.util.*

fun String.convertStringToAlphabets(length: Int = 35): String {
    return this.filterNot { it == '<' || it == '>' || it == '/' || it == '#' || it == '\'' || it == '"' || it == '\\' || it == '{' || it == '}' || it == '[' || it == ']' || it == '!' || it == '%' || it == '$' || it == '?' || it == '.' || it == '=' || it == '~' }
        .take(length)
}

fun Int.addZeroToStart(): String {

    return if (this <= 9) {
        "0$this"
    } else {
        this.toString()
    }

}

fun String.timeStringToDate(): String {

    return try {
        val time = this.filterNot { it == ':' }

        val calender = Calendar.getInstance()

        val year = time.removeRange(startIndex = 4, endIndex = time.lastIndex + 1).toInt()
        val month =
            time.removeRange(startIndex = 0, endIndex = 4)
                .removeRange(startIndex = 2, endIndex = 10)
                .toInt()
        val day =
            time.removeRange(startIndex = 0, endIndex = 6).removeRange(startIndex = 2, endIndex = 8)
                .toInt()
        val hour =
            time.removeRange(startIndex = 0, endIndex = 8).removeRange(startIndex = 2, endIndex = 6)
                .toInt()
        val minute =
            time.removeRange(startIndex = 0, endIndex = 10)
                .removeRange(startIndex = 2, endIndex = 4)
                .toInt()
        val second = time.removeRange(startIndex = 0, endIndex = 12).toInt()

        val currentYear = calender.get(Calendar.YEAR) - year
        val currentMonth = (calender.get(Calendar.MONTH) + 1) - month
        val currentDay = calender.get(Calendar.DAY_OF_MONTH) - day

        val currentHour = calender.get(Calendar.HOUR_OF_DAY) - hour
        val currentMinute = calender.get(Calendar.MINUTE) - minute
        val currentSecond = calender.get(Calendar.SECOND) - second

        "seen ${
            if (currentYear > 0) "$currentYear years " else
                if (currentMonth > 0) "$currentMonth months " else
                    if (currentDay > 0) "$currentDay days " else
                        if (currentHour > 0) "$currentHour hours " else
                            if (currentMinute > 0) "$currentMinute minutes " else
                                if (currentSecond > 0) "$currentSecond seconds" else ""
        } ago"
    } catch (_: Exception) {
        ""
    }

}

fun randomCaptcha(
    length: Int
): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}