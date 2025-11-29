package com.example.todolist.activities

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTaskBinding
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.inputDate.setOnClickListener {
            val c = Calendar.getInstance()
            val dp = DatePickerDialog(
                this,
                { _, y, m, d -> binding.inputDate.setText("$d/${m+1}/$y") },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            dp.show()
        }

        binding.btnSave.setOnClickListener {
            finish()
        }
    }
}