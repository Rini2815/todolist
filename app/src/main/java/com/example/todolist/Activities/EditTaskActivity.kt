package com.example.todolist.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityEditTaskBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import java.text.SimpleDateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTaskBinding
    private var taskId: String = ""
    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var isCalendarVisible = false
    private var isTimePickerVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTaskData()
        setupListeners()
        setupCalendar()
        setupTimePicker()
    }

    private fun loadTaskData() {
        taskId = intent.getStringExtra("taskId") ?: ""

        val task = TaskRepository.getTaskById(taskId)

        if (task != null) {
            binding.edtTitle.setText(task.title)
            binding.edtDesc.setText(task.description)
            binding.edtDate.setText(task.date)
            binding.edtTime.setText(convertTo24Hour(task.time))

            selectedDate = task.date
            selectedTime = convertTo24Hour(task.time)

            binding.switchFavorite.isChecked = task.isFavorite
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.edtDate.setOnClickListener { toggleCalendar() }
        binding.btnCalendar.setOnClickListener { toggleCalendar() }

        binding.edtTime.setOnClickListener { toggleTimePicker() }
        binding.btnClock.setOnClickListener { toggleTimePicker() }

        binding.btnUpdateTask.setOnClickListener { saveTask() }
    }

    private fun setupCalendar() {
        binding.calendarView.minDate = System.currentTimeMillis()

        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            selectedDate = String.format("%02d/%02d/%d", day, month + 1, year)
            binding.edtDate.setText(selectedDate)
            hideCalendar()
        }
    }

    private fun toggleCalendar() {
        if (isCalendarVisible) hideCalendar()
        else {
            showCalendar()
            if (isTimePickerVisible) hideTimePicker()
        }
    }

    private fun showCalendar() {
        binding.cardCalendar.visibility = View.VISIBLE
        isCalendarVisible = true
    }

    private fun hideCalendar() {
        binding.cardCalendar.visibility = View.GONE
        isCalendarVisible = false
    }

    private fun setupTimePicker() {
        binding.timePicker.setIs24HourView(true)

        binding.btnOkTime.setOnClickListener {
            val hour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                binding.timePicker.hour else binding.timePicker.currentHour

            val minute = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                binding.timePicker.minute else binding.timePicker.currentMinute

            selectedTime = String.format("%02d:%02d", hour, minute)
            binding.edtTime.setText(selectedTime)

            hideTimePicker()
        }

        binding.btnCancelTime.setOnClickListener { hideTimePicker() }
    }

    private fun toggleTimePicker() {
        if (isTimePickerVisible) hideTimePicker()
        else {
            showTimePicker()
            if (isCalendarVisible) hideCalendar()
        }
    }

    private fun showTimePicker() {
        binding.cardTimePicker.visibility = View.VISIBLE
        isTimePickerVisible = true
    }

    private fun hideTimePicker() {
        binding.cardTimePicker.visibility = View.GONE
        isTimePickerVisible = false
    }

    private fun saveTask() {
        val title = binding.edtTitle.text.toString().trim()
        val desc = binding.edtDesc.text.toString().trim()
        val isFavorite = binding.switchFavorite.isChecked

        if (title.isEmpty()) {
            binding.edtTitle.error = "Judul tidak boleh kosong"
            return
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Pilih tanggal", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedTime.isEmpty()) {
            Toast.makeText(this, "Pilih waktu", Toast.LENGTH_SHORT).show()
            return
        }

        val task = TaskRepository.getTaskById(taskId)

        if (task != null) {
            task.title = title
            task.description = desc
            task.date = selectedDate
            task.time = formatTime(selectedTime)
            task.isFavorite = isFavorite

            TaskRepository.updateTask(task)

            Toast.makeText(this, "Tugas berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Task tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatTime(time24: String): String {
        return try {
            val inFmt = SimpleDateFormat("HH:mm", Locale.getDefault())
            val outFmt = SimpleDateFormat("h:mm a", Locale.getDefault())
            val date = inFmt.parse(time24)
            outFmt.format(date ?: Date())
        } catch (e: Exception) {
            time24
        }
    }

    private fun convertTo24Hour(time12: String): String {
        return try {
            val inFmt = SimpleDateFormat("h:mm a", Locale.getDefault())
            val outFmt = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = inFmt.parse(time12)
            outFmt.format(date ?: Date())
        } catch (e: Exception) {
            time12
        }
    }
}