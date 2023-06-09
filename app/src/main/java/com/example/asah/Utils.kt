package com.example.asah

import java.util.*

fun getDate(c: Calendar = Calendar.getInstance()) : String {
    val year = c.get(Calendar.YEAR).toString()
    val month = c.get(Calendar.MONTH).toString()
    val day_week = c.get(Calendar.DAY_OF_WEEK)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val hari = arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

    val date = "${hari[day_week-1]} - $day/${month.toInt() + 1}/$year"

    return date
}



