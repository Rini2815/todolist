package com.example.todolist.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    private val timeFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))

    fun formatDate(date: Date?): String {
        return date?.let { dateFormat.format(it) } ?: "-"
    }

    fun formatTime(date: Date?): String {
        return date?.let { timeFormat.format(it) } ?: "-"
    }
}
