package com.example.todolist.Activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityEditTaskBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTaskBinding

    private var taskId = -1
    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 INIT VIEW BINDING
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil ID task dari intent
        taskId = intent.getIntExtra("TASK_ID", -1)
        val task = TaskRepository.getTaskById(taskId)

        // Isi data awal
        if (task != null) {
            binding.etTitle.setText(task.title)
            binding.etDescription.setText(task.description)
            binding.etDate.setText(task.date)
            binding.etTime.setText(task.time)

            selectedDate = task.date
            selectedTime = task.time
        }

        // ============================
        // PILIH TANGGAL
        // ============================
        binding.etDate.setOnClickListener {
            val c = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, y, m, d ->
                    selectedDate = "$d/${m + 1}/$y"
                    binding.etDate.setText(selectedDate)
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // ============================
        // PILIH WAKTU
        // ============================
        binding.etTime.setOnClickListener {
            val c = Calendar.getInstance()

            TimePickerDialog(
                this,
                { _, h, m ->
                    selectedTime = String.format("%02d:%02d", h, m)
                    binding.etTime.setText(selectedTime)
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            ).show()
        }

        // ============================
        // UPDATE TASK
        // ============================
        binding.btnUpdateTask.setOnClickListener {
            val updated = Task(
                id = taskId,
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                date = selectedDate,
                time = selectedTime,
                isFavorite = task?.isFavorite ?: false
            )

            TaskRepository.updateTask(updated)
            finish()
        }

        // ============================
        // TOMBOL BACK
        // ============================
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
