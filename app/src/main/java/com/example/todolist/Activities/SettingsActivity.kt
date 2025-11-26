package com.example.todolist.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        backButton.setOnClickListener { finish() }
    }
}