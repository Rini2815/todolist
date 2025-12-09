package com.example.todolist.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.activities.AddTaskActivity
import com.example.todolist.activities.DetailTaskActivity
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.model.Task
import com.example.todolist.model.TaskRepository
import com.example.todolist.utils.TaskAlarmReceiver   // <-- FIX IMPORT

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
                intent.putExtra("taskDate", task.date)
                startActivity(intent)
            },
            onToggle = { task, isChecked ->
                TaskRepository.setDone(task.id, isChecked)

                if (isChecked) {
                    cancelAlarm(task)
                    android.widget.Toast.makeText(
                        requireContext(),
                        "✅ ${task.title} selesai! Alarm dibatalkan.",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                } else {
                    android.widget.Toast.makeText(
                        requireContext(),
                        "↩️ ${task.title} belum selesai. Alarm aktif kembali.",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }

                taskAdapter.notifyDataSetChanged()
                updateEmptyState()
            }
        )

        binding.rvTodayTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter

            try {
                layoutAnimation = AnimationUtils.loadLayoutAnimation(
                    requireContext(),
                    R.anim.layout_animation_fade
                )
            } catch (_: Exception) {}
        }
    }

    // ==================================
    //            CANCEL ALARM
    // ==================================
    private fun cancelAlarm(task: Task) {
        try {
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // FIX: Ganti AlarmReceiver -> TaskAlarmReceiver
            val intent = Intent(requireContext(), TaskAlarmReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                task.id.hashCode(), // FIX: harus Int
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()

        } catch (e: Exception) {
            e.printStackTrace()
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
        val list = TaskRepository.getAllTasks()

        if (list.isEmpty()) {
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