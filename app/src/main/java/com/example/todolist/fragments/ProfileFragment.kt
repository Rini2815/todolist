package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.activities.AboutActivity
import com.example.todolist.activities.HelpActivity
import com.example.todolist.activities.SettingsActivity  // ← TAMBAHKAN IMPORT INI
import com.example.todolist.databinding.FragmentProfileBinding

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

        // Menu Pengaturan ← TAMBAHKAN INI
        binding.menuPengaturan.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        // Menu Pertanyaan
        binding.menuHelp.setOnClickListener {
            startActivity(Intent(requireContext(), HelpActivity::class.java))
        }

        // Menu Tentang To-Do List
        binding.menuAbout.setOnClickListener {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}