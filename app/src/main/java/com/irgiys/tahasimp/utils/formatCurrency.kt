package com.irgiys.tahasimp.utils

import java.text.NumberFormat
import java.util.*

fun formatCurrency(amount: Long?): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
    formatter.isGroupingUsed = true
    val formattedAmount = formatter.format(amount)
    return "Rp$formattedAmount"
}
