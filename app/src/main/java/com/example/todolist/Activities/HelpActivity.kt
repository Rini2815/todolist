package com.example.todolist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hilangkan action bar
        supportActionBar?.hide()

        // Tombol back
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}