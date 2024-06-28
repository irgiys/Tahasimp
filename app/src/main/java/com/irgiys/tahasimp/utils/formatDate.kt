package com.irgiys.tahasimp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(date: Date?): String {
    val format = SimpleDateFormat("yyyy/MM/dd hh:mm aaa", Locale.getDefault())
    return format.format(date)
}
