package com.irgiys.tahasimp.utils

fun parseStringToLong(input: String): Long {
    // Hapus karakter non-digit
    val cleanString = input.replace("[^\\d]".toRegex(), "")

    // Ubah string menjadi Long
    return cleanString.toLong()
}
