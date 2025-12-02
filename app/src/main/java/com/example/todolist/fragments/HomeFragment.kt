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

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()

        // Tombol tambah task
        binding.btnAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        // Gunakan constructor TaskAdapter dengan 2 parameter saja
        taskAdapter = TaskAdapter(
            TaskRepository.getAllTasks(),  // list task
            onClick = { task ->           // klik detail task
                val intent = Intent(requireContext(), DetailTaskActivity::class.java)
                intent.putExtra("taskId", task.id)
                startActivity(intent)
            }
        )

        binding.rvTodayTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh daftar task
        taskAdapter.notifyDataSetChanged()
    }
}
