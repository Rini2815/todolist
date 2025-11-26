package com.example.todolist.Activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pilih tanggal
        binding.addTaskDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    selectedDate = "$d/${m + 1}/$y"
                    binding.addTaskDate.setText(selectedDate)
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Pilih waktu
        binding.addTaskTime.setOnClickListener {
            val c = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, h, m ->
                    selectedTime = "$h:$m"
                    binding.addTaskTime.setText(selectedTime)
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            ).show()
        }

        // Simpan Task
        binding.saveTaskBtn.setOnClickListener {
            val title = binding.addTaskTitle.text.toString()
            val desc = binding.addTaskDesc.text.toString()

            if (title.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {

                val id = TaskRepository.getAllTasks().size + 1

                TaskRepository.addTask(
                    Task(
                        id = id,
                        title = title,
                        description = desc,
                        date = selectedDate,
                        time = selectedTime
                    )
                )

                finish()
            }
        }

        // Kembali
        binding.backButton.setOnClickListener { finish() }
    }
}
