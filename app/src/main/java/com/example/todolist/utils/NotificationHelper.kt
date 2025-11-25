// /app/src/main/java/com/todolist/utils/NotificationHelper.kt
package com.example.todolist.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.todolist.R

object NotificationHelper {

    private const val CHANNEL_ID = "task_reminder_channel"
    private const val CHANNEL_NAME = "Task Reminders"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }


    fun showTaskReminder(context: Context, title: String) {
        createNotificationChannel(context)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)   // tambahkan icon ini ke drawable
            .setContentTitle("Pengingat Tugas")
            .setContentText("Deadline: $title")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val id = System.currentTimeMillis().toInt()
        notificationManager.notify(id, notification)
    }


    fun scheduleRepeatingReminder(context: Context, taskId: String, taskTitle: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", taskTitle)
        }

        val pending = PendingIntent.getBroadcast(
            context,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val interval = 60 * 60 * 1000 // 1 jam

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + interval,
            interval.toLong(),
            pending
        )
    }


    fun cancelReminder(context: Context, taskId: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java)
        val pending = PendingIntent.getBroadcast(
            context,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pending)
    }
}
