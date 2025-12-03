package com.example.todolist.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {

    private const val CHANNEL_ID = "todolist_channel"

    fun showNotification(context: Context, title: String, message: String) {

        // Ambil NotificationManager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Buat channel untuk Android 8+ (Oreo ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "ToDoList Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Buat notifikasi
        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // ikon default Android
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        // Tampilkan notifikasi
        notificationManager.notify(1001, notif)
    }
}
