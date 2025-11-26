package com.example.todolist.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityDetailTaskBinding
import com.example.todolist.model.TaskRepository

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTaskBinding
    private var taskId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Inisialisasi ViewBinding
        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil ID Task dari Intent
        taskId = intent.getIntExtra("TASK_ID", -1)
        val task = TaskRepository.getTaskById(taskId)

        // Tampilkan data
        if (task != null) {
            binding.detailTitle.text = task.title
            binding.detailDesc.text = task.description
            binding.detailDate.text = task.date
            binding.detailTime.text = task.time
        }

        // Tombol Edit
        binding.editTaskBtn.setOnClickListener {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("TASK_ID", taskId)
            startActivity(intent)
        }

        // Tombol Delete
        binding.deleteTaskBtn.setOnClickListener {
            TaskRepository.deleteTask(taskId)
            finish()
        }

        // Tombol Back
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
