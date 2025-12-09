package com.example.todolist.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private var selectedDate: String = ""
    private var isCalendarVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupCalendar()
    }

    private fun setupListeners() {
        // Tombol back
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Klik input tanggal atau icon kalender
        binding.edtDate.setOnClickListener {
            toggleCalendar()
        }

        binding.btnCalendar.setOnClickListener {
            toggleCalendar()
        }

        // Button simpan tugas
        binding.btnSaveTask.setOnClickListener {
            saveTask()
        }
    }

    private fun setupCalendar() {
        // Set tanggal minimum (hari ini)
        binding.calendarView.minDate = System.currentTimeMillis()

        // Listener ketika tanggal dipilih
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Format: DD/MM/YYYY
            selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            binding.edtDate.setText(selectedDate)

            // Hide kalender setelah pilih tanggal
            hideCalendar()
        }
    }

    private fun toggleCalendar() {
        if (isCalendarVisible) {
            hideCalendar()
        } else {
            showCalendar()
        }
    }

    private fun showCalendar() {
        binding.cardCalendar.visibility = View.VISIBLE
        isCalendarVisible = true

        // Smooth scroll ke kalender
        binding.root.post {
            binding.root.smoothScrollTo(0, binding.cardCalendar.top)
        }
    }

    private fun hideCalendar() {
        binding.cardCalendar.visibility = View.GONE
        isCalendarVisible = false
    }

    private fun saveTask() {
        val title = binding.edtTitle.text.toString().trim()
        val desc = binding.edtDesc.text.toString().trim()
        val isFavorite = binding.switchFavorite.isChecked
        val hasReminder = binding.switchReminder.isChecked

        // Validasi input
        if (title.isEmpty()) {
            binding.edtTitle.error = "Judul tidak boleh kosong"
            binding.edtTitle.requestFocus()
            return
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Pilih tanggal terlebih dahulu", Toast.LENGTH_SHORT).show()
            binding.edtDate.requestFocus()
            return
        }

        // Generate waktu saat ini untuk ditampilkan (format: 1:35 PM)
        val currentTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())

        // Buat task baru
        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            description = desc,
            time = currentTime,
            isDone = false,
            isFavorite = isFavorite
        )

        // Simpan ke repository
        TaskRepository.addTask(newTask)

        // TODO: Setup notification jika hasReminder == true
        if (hasReminder) {
            setupNotification(newTask, selectedDate)
        }

        // Tampilkan toast sukses
        Toast.makeText(this, " Tugas berhasil ditambahkan!", Toast.LENGTH_SHORT).show()

        // Kembali ke home
        finish()
    }

    private fun setupNotification(task: Task, date: String) {
        // TODO: Implement notification dengan AlarmManager
        // Untuk saat ini just placeholder
        Toast.makeText(this, " Pengingat akan diaktifkan untuk $date", Toast.LENGTH_SHORT).show()
    }
}