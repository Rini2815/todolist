package com.example.todolist.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import com.example.todolist.utils.TaskAlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var selectedDateMillis: Long = 0

    private var isCalendarVisible = false
    private var isTimePickerVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchReminder.isChecked = true // default ON

        setupListeners()
        setupCalendar()
        setupTimePicker()
    }

    // ---------------------------------------------------
    // LISTENER BUTTON DAN INPUT
    // ---------------------------------------------------
    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.edtDate.setOnClickListener { toggleCalendar() }
        binding.btnCalendar.setOnClickListener { toggleCalendar() }

        binding.edtTime.setOnClickListener { toggleTimePicker() }
        binding.btnClock.setOnClickListener { toggleTimePicker() }

        binding.btnSaveTask.setOnClickListener { saveTask() }
    }

    // ---------------------------------------------------
    // SETUP CALENDAR
    // ---------------------------------------------------
    private fun setupCalendar() {
        binding.calendarView.minDate = System.currentTimeMillis()

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            binding.edtDate.setText(selectedDate)

            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            selectedDateMillis = cal.timeInMillis

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

        binding.root.post {
            binding.root.smoothScrollTo(0, binding.cardCalendar.top)
        }
    }

    private fun hideCalendar() {
        binding.cardCalendar.visibility = View.GONE
        isCalendarVisible = false
    }

    // ---------------------------------------------------
    // SETUP TIME PICKER
    // ---------------------------------------------------
    private fun setupTimePicker() {
        binding.timePicker.setIs24HourView(true)

        binding.btnOkTime.setOnClickListener {
            val hour =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) binding.timePicker.hour
                else binding.timePicker.currentHour

            val minute =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) binding.timePicker.minute
                else binding.timePicker.currentMinute

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

        binding.root.post {
            binding.root.smoothScrollTo(0, binding.cardTimePicker.top)
        }
    }

    private fun hideTimePicker() {
        binding.cardTimePicker.visibility = View.GONE
        isTimePickerVisible = false
    }

    // ---------------------------------------------------
    // SAVE TASK
    // ---------------------------------------------------
    private fun saveTask() {
        val title = binding.edtTitle.text.toString().trim()
        val desc = binding.edtDesc.text.toString().trim()
        val isFavorite = binding.switchFavorite.isChecked
        val hasReminder = binding.switchReminder.isChecked

        if (title.isEmpty()) {
            binding.edtTitle.error = "Judul tidak boleh kosong"
            return
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "⚠️ Pilih tanggal terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedTime.isEmpty()) {
            Toast.makeText(this, "⚠️ Pilih waktu terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        val timeDisplay = formatTime(selectedTime)

        val task = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            description = desc,
            time = timeDisplay,
            date = selectedDate,
            isDone = false,
            isFavorite = isFavorite
        )

        TaskRepository.addTask(task)

        if (hasReminder) {
            setupAlarm(task)
        }

        Toast.makeText(this, "✅ Tugas berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
        finish()
    }

    // Format waktu ke 1:25 PM
    private fun formatTime(time: String): String {
        return try {
            val sdf24 = SimpleDateFormat("HH:mm", Locale.getDefault())
            val sdf12 = SimpleDateFormat("h:mm a", Locale.getDefault())
            val date = sdf24.parse(time)
            sdf12.format(date!!)
        } catch (e: Exception) {
            time
        }
    }

    // ---------------------------------------------------
    // ALARM MANAGER
    // ---------------------------------------------------
    private fun setupAlarm(task: Task) {
        try {
            val (hour, minute) = selectedTime.split(":").map { it.toInt() }

            val cal = Calendar.getInstance()
            cal.timeInMillis = selectedDateMillis
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            cal.set(Calendar.SECOND, 0)

            if (cal.timeInMillis < System.currentTimeMillis()) {
                Toast.makeText(this, "⚠️ Waktu pengingat sudah lewat!", Toast.LENGTH_SHORT).show()
                return
            }

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(this, TaskAlarmReceiver::class.java).apply {
                putExtra("task_title", task.title)
                putExtra("task_desc", task.description)
            }

            val pending = PendingIntent.getBroadcast(
                this,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    cal.timeInMillis,
                    pending
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    cal.timeInMillis,
                    pending
                )
            }

            Toast.makeText(this, "🔔 Pengingat diatur!", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}