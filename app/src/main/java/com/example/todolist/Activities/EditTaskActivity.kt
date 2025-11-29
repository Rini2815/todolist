package com.example.todolist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityEditTaskBinding

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            finish()
        }
    }
}