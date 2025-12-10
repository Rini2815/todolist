package com.example.todolist.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.utils.MyPreferenceManager

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: MyPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = MyPreferenceManager(this)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<LinearLayout>(R.id.menuEditProfile).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.menuNotification).setOnClickListener {
            startActivity(Intent(this, NotificationSettingsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.menuPrivacy).setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.menuShare).setOnClickListener {
            shareApp()
        }
    }

    private fun shareApp() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Coba aplikasi To-Do List ku 😄")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Bagikan aplikasi via...")
        startActivity(shareIntent)
    }
}
