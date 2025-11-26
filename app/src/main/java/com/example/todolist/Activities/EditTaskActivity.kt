package com.example.todolist.Activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import kotlinx.android.synthetic.main.activity_edit_task.*
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private var taskId = -1
    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        taskId = intent.getIntExtra("TASK_ID", -1)
        val task = TaskRepository.getTaskById(taskId)

        if (task != null) {
            editTitle.setText(task.title)
            editDesc.setText(task.description)
            editDate.setText(task.date)
            editTime.setText(task.time)

            selectedDate = task.date
            selectedTime = task.time
        }

        editDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    selectedDate = "$d/${m + 1}/$y"
                    editDate.setText(selectedDate)
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        editTime.setOnClickListener {
            val c = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, h, m ->
                    selectedTime = "$h:$m"
                    editTime.setText(selectedTime)
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            ).show()
        }

        updateTaskBtn.setOnClickListener {
            val updated = Task(
                id = taskId,
                title = editTitle.text.toString(),
                description = editDesc.text.toString(),
                date = selectedDate,
                time = selectedTime,
                isFavorite = task?.isFavorite ?: false
            )
            TaskRepository.updateTask(updated)
            finish()
        }

        backButton.setOnClickListener { finish() }
    }
}