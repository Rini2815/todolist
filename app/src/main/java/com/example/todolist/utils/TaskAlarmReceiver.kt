package com.example.todolist.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra("task_title") ?: "Tugas"
        val desc  = intent.getStringExtra("task_desc") ?: "Ada tugas yang harus kamu kerjakan."

        NotificationHelper.showNotification(
            context,
            title,
            desc
        )
    }
}