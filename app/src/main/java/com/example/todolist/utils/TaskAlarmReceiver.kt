package com.example.todolist.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("task_title") ?: "Tugas"
        val message = intent?.getStringExtra("task_message") ?: "Waktunya mengerjakan tugas!"

        NotificationHelper.showNotification(context, title, message)
    }
}