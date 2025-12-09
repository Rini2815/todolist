package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.activities.AddTaskActivity
import com.example.todolist.activities.DetailTaskActivity
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.model.TaskRepository
import android.view.animation.AnimationUtils
import com.example.todolist.R

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupListeners()
        updateEmptyState()

        return binding.root
    }

    // ==================================
    //         SETUP RECYCLER VIEW
    // ==================================
    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            TaskRepository.getAllTasks(),
            onClick = { task ->
                val intent = Intent(requireContext(), DetailTaskActivity::class.java)
                intent.putExtra("taskId", task.id)
                intent.putExtra("taskTitle", task.title)
                intent.putExtra("taskDesc", task.description)
                intent.putExtra("taskTime", task.time)
                intent.putExtra("taskDate", task.date)   // ✔ ditambahkan
                startActivity(intent)
            },
            onToggle = { task, isChecked ->
                TaskRepository.setDone(task.id, isChecked)
                taskAdapter.notifyDataSetChanged()
                updateEmptyState()
            }
        )

        binding.rvTodayTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter

            // ✔ animasi list (kalau file-nya ada)
            try {
                layoutAnimation = AnimationUtils.loadLayoutAnimation(
                    requireContext(),
                    R.anim.layout_animation_fade
                )
            } catch (_: Exception) { }
        }
    }

    // ==================================
    //             LISTENERS
    // ==================================
    private fun setupListeners() {
        binding.fabAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }
    }

    // ==================================
    //         EMPTY STATE HANDLER
    // ==================================
    private fun updateEmptyState() {
        val taskList = TaskRepository.getAllTasks()

        if (taskList.isEmpty()) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvTodayTasks.visibility = View.GONE
        } else {
            binding.emptyState.visibility = View.GONE
            binding.rvTodayTasks.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        taskAdapter.notifyDataSetChanged()
        updateEmptyState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}