package com.example.todolist.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskTitle = intent.getStringExtra("task_title") ?: "Pengingat Tugas"
        val taskDesc = intent.getStringExtra("task_desc") ?: "Anda memiliki tugas yang harus dikerjakan"
        val taskId = intent.getStringExtra("task_id")

        Log.d("TaskAlarmReceiver", "Alarm triggered for: $taskTitle")

        NotificationHelper.showNotification(
            context = context,
            title = "⏰ $taskTitle",
            message = taskDesc,
            taskId = taskId
        )
    }
}