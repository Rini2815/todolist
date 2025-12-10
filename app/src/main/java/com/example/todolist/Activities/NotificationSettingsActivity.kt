package com.example.todolist.activities

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.utils.MyPreferenceManager

class NotificationSettingsActivity : AppCompatActivity() {

    private lateinit var prefs: MyPreferenceManager

    private lateinit var switchNotification: Switch
    private lateinit var switchVibrate: Switch
    private lateinit var btnSelectSound: Button
    private lateinit var tvSelectedSound: TextView
    private var selectedSoundUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)

        prefs = MyPreferenceManager(this)

        switchNotification = findViewById(R.id.switchNotification)
        switchVibrate = findViewById(R.id.switchVibrate)
        btnSelectSound = findViewById(R.id.btnSelectSound)
        tvSelectedSound = findViewById(R.id.tvSelectedSound)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        loadSettings()

        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            prefs.putBoolean("notification_enabled", isChecked)
            Toast.makeText(
                this,
                "Notifikasi ${if (isChecked) "diaktifkan" else "dinonaktifkan"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        switchVibrate.setOnCheckedChangeListener { _, isChecked ->
            prefs.putBoolean("notification_vibrate", isChecked)
            Toast.makeText(
                this,
                "Getar ${if (isChecked) "diaktifkan" else "dinonaktifkan"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnSelectSound.setOnClickListener {
            val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            selectedSoundUri = ringtoneUri
            prefs.putString("notification_sound", ringtoneUri.toString())
            tvSelectedSound.text = "Nada: Default"
            Toast.makeText(this, "Nada notifikasi dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSettings() {
        // Load status switch
        switchNotification.isChecked = prefs.getBoolean("notification_enabled", true)
        switchVibrate.isChecked = prefs.getBoolean("notification_vibrate", true)

        val soundUriString = prefs.getString("notification_sound")
        selectedSoundUri = if (!soundUriString.isNullOrEmpty()) Uri.parse(soundUriString) else null
        tvSelectedSound.text = if (selectedSoundUri != null) "Nada: Default" else "Nada: Tidak dipilih"
    }
}
