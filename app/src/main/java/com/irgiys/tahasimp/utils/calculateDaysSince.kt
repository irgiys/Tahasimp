package com.irgiys.tahasimp.utils

import java.util.Date
import java.util.concurrent.TimeUnit

fun calculateDaysSince(date: Date): Long {
    val currentDate = Date()
    val diff = currentDate.time - date.time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
}
