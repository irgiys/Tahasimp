package com.irgiys.tahasimp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(date: Date?): String {
    val format = SimpleDateFormat("HH:mm dd MMM yyyy", Locale.getDefault())
    return format.format(date)
}
