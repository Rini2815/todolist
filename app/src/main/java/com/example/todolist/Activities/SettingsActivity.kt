package com.example.todolist.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Back Button
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Theme
        findViewById<LinearLayout>(R.id.menuTheme).setOnClickListener {
            Toast.makeText(this, "Fitur Theme akan segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // Privacy Policy
        findViewById<LinearLayout>(R.id.menuPrivacy).setOnClickListener {
            Toast.makeText(this, "Fitur Privacy Policy akan segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // About App
        findViewById<LinearLayout>(R.id.menuAboutApp).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        // Logout
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Keluar")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                Toast.makeText(this, "Berhasil keluar", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}