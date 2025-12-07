package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.todolist.activities.AboutActivity
import com.example.todolist.activities.HelpActivity
import com.example.todolist.activities.LoginActivity
import com.example.todolist.activities.SettingsActivity
import com.example.todolist.databinding.FragmentProfileBinding
import com.example.todolist.model.TaskRepository
import com.example.todolist.utils.MyPreferenceManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserProfile()
        loadTaskStatistics()

        // Menu cepat
        binding.menuPengaturan.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        binding.menuHelp.setOnClickListener {
            startActivity(Intent(requireContext(), HelpActivity::class.java))
        }

        binding.menuAbout.setOnClickListener {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }

        binding.menuLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserProfile()
        loadTaskStatistics()
    }

    private fun loadUserProfile() {
        val prefs = MyPreferenceManager(requireContext())
        binding.tvUsername.text = prefs.getUsername()
        binding.tvEmail.text = prefs.getEmail()
    }

    private fun loadTaskStatistics() {
        val completedCount = TaskRepository.getCompletedTaskCount()
        val pendingCount = TaskRepository.getPendingTaskCount()

        Log.d("ProfileFragment", "Completed: $completedCount, Pending: $pendingCount")

        binding.tvTaskDone.text = completedCount.toString()
        binding.tvTaskPending.text = pendingCount.toString()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Keluar")
            .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setPositiveButton("Ya") { _, _ -> performLogout() }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun performLogout() {
        val prefs = MyPreferenceManager(requireContext())
        prefs.clear()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finishAffinity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
