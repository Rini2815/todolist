package com.example.todolist.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.utils.MyPreferenceManager

class EditProfileActivity : AppCompatActivity() {

    private lateinit var prefs: MyPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        prefs = MyPreferenceManager(this)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<ImageButton>(R.id.btnBack) // tombol back

        etName.setText(prefs.getUsername())
        etEmail.setText(prefs.getString("email") ?: "")

        btnSave.setOnClickListener {
            prefs.setUsername(etName.text.toString())
            prefs.putString("email", etEmail.text.toString())
            finish()
        }

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
