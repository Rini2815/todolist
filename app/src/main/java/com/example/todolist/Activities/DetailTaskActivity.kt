package com.example.todolist.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.model.TaskRepository
import kotlinx.android.synthetic.main.activity_detail_task.*

class DetailTaskActivity : AppCompatActivity() {

    private var taskId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        taskId = intent.getIntExtra("TASK_ID", -1)
        val task = TaskRepository.getTaskById(taskId)

        if (task != null) {
            detailTitle.text = task.title
            detailDesc.text = task.description
            detailDate.text = task.date
            detailTime.text = task.time
        }

        editTaskBtn.setOnClickListener {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("TASK_ID", taskId)
            startActivity(intent)
        }

        deleteTaskBtn.setOnClickListener {
            TaskRepository.deleteTask(taskId)
            finish()
        }

        backButton.setOnClickListener { finish() }
    }
}