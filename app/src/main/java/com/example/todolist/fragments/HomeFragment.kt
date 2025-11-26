package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.model.Task
import com.example.todolist.ui.AddTaskActivity
import com.example.todolist.ui.DetailTaskActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Dummy data
        taskList = mutableListOf(
            Task("Belajar Kotlin", "22 Nov 2025", "Kuliah", false),
            Task("Meeting Client", "23 Nov 2025", "Kerja", true)
        )

        setupRecycler()
        binding.btnAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }

        return binding.root
    }

    private fun setupRecycler() {
        taskAdapter = TaskAdapter(taskList,
            onClick = { item ->
                val i = Intent(requireContext(), DetailTaskActivity::class.java)
                i.putExtra("DATA", item)
                startActivity(i)
            },
            onFavorite = { item ->
                item.isFavorite = !item.isFavorite
            }
        )

        binding.rvTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }
}