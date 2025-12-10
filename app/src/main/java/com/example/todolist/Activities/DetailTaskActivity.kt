package com.example.todolist.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.databinding.ActivityDetailTaskBinding
import com.example.todolist.model.SubTask
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTaskBinding
    private var currentTask: Task? = null
    private var taskId: String = ""

    companion object {
        private const val REQUEST_EDIT_TASK = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTaskData()
        setupListeners()
    }

    private fun loadTaskData() {
        taskId = intent.getStringExtra("taskId") ?: ""

        if (taskId.isNotEmpty()) {
            currentTask = TaskRepository.getTaskById(taskId)
            currentTask?.let { task ->
                binding.tvDetailTitle.text = task.title
                binding.tvDetailDesc.text = task.description
                binding.tvDetailTime.text = task.time
                binding.tvDetailDate.text = task.date
                updateSubtaskList()
            }
        } else {
            // fallback jika intent tidak membawa id
            binding.tvDetailTitle.text = intent.getStringExtra("taskTitle") ?: ""
            binding.tvDetailDesc.text = intent.getStringExtra("taskDesc") ?: ""
            binding.tvDetailTime.text = intent.getStringExtra("taskTime") ?: ""
            binding.tvDetailDate.text = intent.getStringExtra("taskDate") ?: ""
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            saveChanges()
            finish()
        }

        binding.btnMenu.setOnClickListener { showPopupMenu(it) }

        binding.btnAddCard.setOnClickListener { addSubTask() }

        binding.btnCancelCard.setOnClickListener { clearInput() }
    }

    private fun addSubTask() {
        val text = binding.inputCard.text.toString().trim()

        if (text.isEmpty()) {
            binding.inputCard.error = "Card tidak boleh kosong"
            return
        }

        currentTask?.let { task ->
            val newSubtask = SubTask(text = text, isDone = false)
            task.subtasks.add(newSubtask)

            TaskRepository.updateTask(task)
            updateSubtaskList()
            clearInput()

            Toast.makeText(this, "✅ Card ditambahkan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearInput() {
        binding.inputCard.setText("")
        binding.inputCard.clearFocus()
    }

    private fun updateSubtaskList() {
        binding.listCard.removeAllViews()

        currentTask?.subtasks?.forEachIndexed { index, subtask ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_subtask_card, binding.listCard, false)

            val checkbox = itemView.findViewById<CheckBox>(R.id.checkboxSubtask)
            val txtSubtask = itemView.findViewById<TextView>(R.id.txtSubtask)
            val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDeleteSubtask)

            txtSubtask.text = subtask.text
            checkbox.isChecked = subtask.isDone

            // tampilkan strike-through jika done
            applyStrikeThrough(txtSubtask, subtask.isDone)

            checkbox.setOnCheckedChangeListener { _, checked ->
                subtask.isDone = checked
                applyStrikeThrough(txtSubtask, checked)
                saveChanges()
            }

            btnDelete.setOnClickListener {
                showDeleteDialog(index, subtask)
            }

            binding.listCard.addView(itemView)
        }
    }

    private fun applyStrikeThrough(textView: TextView, done: Boolean) {
        if (done) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.alpha = 0.6f
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.alpha = 1f
        }
    }

    private fun showDeleteDialog(index: Int, subtask: SubTask) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Card")
            .setMessage("Apakah Anda yakin ingin menghapus \"${subtask.text}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                currentTask?.let { task ->
                    task.subtasks.removeAt(index)
                    TaskRepository.updateTask(task)
                    updateSubtaskList()
                    Toast.makeText(this, "🗑️ Card dihapus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_detail_task, popup.menu)

        val favoriteItem = popup.menu.findItem(R.id.action_favorite)
        val isFav = currentTask?.isFavorite ?: false
        favoriteItem.title = if (isFav) "Hapus dari Favorit" else "Tambah ke Favorit"

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> { editTask(); true }
                R.id.action_favorite -> { toggleFavorite(); true }
                R.id.action_share -> { shareTask(); true }
                R.id.action_delete -> { deleteTask(); true }
                else -> false
            }
        }

        popup.show()
    }

    private fun editTask() {
        currentTask?.let { task ->
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("taskId", task.id)
            startActivityForResult(intent, REQUEST_EDIT_TASK)
        }
    }

    private fun toggleFavorite() {
        currentTask?.let { task ->
            task.isFavorite = !task.isFavorite
            TaskRepository.updateTask(task)

            Toast.makeText(
                this,
                if (task.isFavorite) "❤️ Ditambahkan ke Favorit"
                else "💔 Dihapus dari Favorit",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun shareTask() {
        currentTask?.let { task ->
            val shareText = buildString {
                append("📋 ${task.title}\n\n")
                append("${task.description}\n\n")
                append("📅 ${task.date} | ⏰ ${task.time}\n\n")

                if (task.subtasks.isNotEmpty()) {
                    append("Subtasks:\n")
                    task.subtasks.forEachIndexed { i, sub ->
                        append("${i + 1}. ${if (sub.isDone) "✓" else "○"} ${sub.text}\n")
                    }
                }
            }

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_SUBJECT, task.title)
            }

            startActivity(Intent.createChooser(sendIntent, "Bagikan via"))
        }
    }

    private fun deleteTask() {
        AlertDialog.Builder(this)
            .setTitle("Hapus Tugas")
            .setMessage("Apakah Anda yakin ingin menghapus tugas ini?")
            .setPositiveButton("Hapus") { _, _ ->
                currentTask?.let { task ->
                    TaskRepository.deleteTask(task.id)
                }
                Toast.makeText(this, "🗑️ Tugas dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun saveChanges() {
        currentTask?.let { TaskRepository.updateTask(it) }
    }

    override fun onBackPressed() {
        saveChanges()
        super.onBackPressed()
    }

    override fun onActivityResult(req: Int, res: Int, data: Intent?) {
        super.onActivityResult(req, res, data)
        if (req == REQUEST_EDIT_TASK && res == RESULT_OK) {
            loadTaskData()
        }
    }
}