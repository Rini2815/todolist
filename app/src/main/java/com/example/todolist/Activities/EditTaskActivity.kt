package com.example.todolist.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityEditTaskBinding
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTaskBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Activity sebelumnya (jika ada)
        binding.editTitle.setText(intent.getStringExtra("title") ?: "")
        binding.editDesc.setText(intent.getStringExtra("desc") ?: "")
        binding.editDate.setText(intent.getStringExtra("date") ?: "")
        binding.editTime.setText(intent.getStringExtra("time") ?: "")

        setupListeners()
    }

    private fun setupListeners() {

        // Date picker
        binding.editDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                binding.editDate.setText("$d/${m + 1}/$y")
            }, year, month, day).show()
        }

        // Time picker
        binding.editTime.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, h, m ->
                binding.editTime.setText(String.format("%02d:%02d", h, m))
            }, hour, minute, true).show()
        }

        // Button Update
        binding.btnUpdateTask.setOnClickListener {

            val title = binding.editTitle.text.toString().trim()
            val desc = binding.editDesc.text.toString().trim()

            if (title.isEmpty()) {
                binding.editTitle.error = "Judul tidak boleh kosong"
                return@setOnClickListener
            }

            // Kirim hasil update kembali
            val resultIntent = intent
            resultIntent.putExtra("updated_title", title)
            resultIntent.putExtra("updated_desc", desc)
            resultIntent.putExtra("updated_date", binding.editDate.text.toString())
            resultIntent.putExtra("updated_time", binding.editTime.text.toString())

            setResult(RESULT_OK, resultIntent)

            Toast.makeText(this, "Tugas berhasil diperbarui", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
