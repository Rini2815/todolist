// /app/src/main/java/com/todolist/utils/ReminderReceiver.kt
package com.example.todolist.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Tugas"
        NotificationHelper.showTaskReminder(context, title)
    }
}
