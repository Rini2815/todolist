package com.example.todolist.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.databinding.ActivityDetailTaskBinding

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTaskBinding
    private val subTasks = arrayListOf<String>()
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        binding.btnAddCard.setOnClickListener {
            val text = binding.inputCard.text.toString()
            if (text.isNotEmpty()) {
                subTasks.add(text)
                updateList()
                binding.inputCard.setText("")
            }
        }
    }

    private fun updateList() {
        binding.listCard.removeAllViews()

        subTasks.forEach { t ->
            val item = layoutInflater.inflate(R.layout.item_task, null)

            val check = item.findViewById<android.widget.CheckBox>(R.id.checkboxTask)
            val txt = item.findViewById<android.widget.TextView>(R.id.txtTask)

            txt.text = t

            check.setOnCheckedChangeListener { _, ch ->
                txt.paintFlags = if (ch) {
                    txt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    txt.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            binding.listCard.addView(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.button_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_share -> {
                val send = Intent(Intent.ACTION_SEND)
                send.type = "text/plain"
                send.putExtra(Intent.EXTRA_TEXT, "Bagikan tugas")
                startActivity(Intent.createChooser(send, "Share via"))
            }

            R.id.menu_favorite -> {
                isFavorite = !isFavorite
            }
        }
        return true
    }
}