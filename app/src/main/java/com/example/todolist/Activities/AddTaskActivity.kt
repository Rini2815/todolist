package com.example.todolist.Activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // Tanggal
        addTaskDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    selectedDate = "$d/${m + 1}/$y"
                    addTaskDate.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Waktu
        addTaskTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, h, m ->
                    selectedTime = "$h:$m"
                    addTaskTime.setText(selectedTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        // Simpan
        saveTaskBtn.setOnClickListener {
            val title = addTaskTitle.text.toString()
            val desc = addTaskDesc.text.toString()

            if (title.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {

                val id = TaskRepository.getTasks().size + 1

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

        backButton.setOnClickListener { finish() }
    }
}