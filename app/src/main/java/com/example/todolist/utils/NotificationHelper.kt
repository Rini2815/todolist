package com.example.todolist.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.todolist.R
import com.example.todolist.activities.HomeActivity

object NotificationHelper {

    private const val CHANNEL_ID = "todolist_channel"
    private const val CHANNEL_NAME = "ToDoList Notification"

    fun showNotification(context: Context, title: String, message: String) {

        // Cek apakah notifikasi diaktifkan di settings
        val prefs = MyPreferenceManager(context)
        val isNotificationEnabled = prefs.getBoolean("notification_enabled", true)

        if (!isNotificationEnabled) {
            return // Jangan tampilkan notifikasi jika dimatikan
        }

        // Ambil NotificationManager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Buat channel untuk Android 8+ (Oreo ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi pengingat tugas"
                enableLights(true)
                enableVibration(true)

                // Set sound dari preferences atau default
                val soundUriString = prefs.getString("notification_sound")
                val soundUri = if (!soundUriString.isNullOrEmpty()) {
                    Uri.parse(soundUriString)
                } else {
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                }
                setSound(soundUri, null)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Intent untuk membuka aplikasi saat notifikasi diklik
        val intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Ambil setting sound dan vibrate
        val isVibrateEnabled = prefs.getBoolean("notification_vibrate", true)

        // Sound URI
        val soundUriString = prefs.getString("notification_sound")
        val soundUri = if (!soundUriString.isNullOrEmpty()) {
            Uri.parse(soundUriString)
        } else {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }

        // Buat notifikasi
        val notifBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // Pastikan icon ini ada
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSound(soundUri)

        // Tambahkan vibrate jika diaktifkan
        if (isVibrateEnabled) {
            notifBuilder.setVibrate(longArrayOf(0, 500, 250, 500))
        }

        val notification = notifBuilder.build()

        // Tampilkan notifikasi
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    // Method untuk request permission notifikasi (Android 13+)
    fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            true // Permission otomatis granted untuk Android < 13
        }
    }
}