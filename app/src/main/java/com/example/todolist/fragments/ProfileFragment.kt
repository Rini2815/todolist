package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.activities.SettingsActivity
import com.example.todolist.databinding.FragmentProfileBinding
import com.example.todolist.utils.PreferenceManager

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val prefs = PreferenceManager(requireContext())
        val name = prefs.getUserName()

        binding.tvUserName.text = "Hi $name!"

        binding.menuPengaturan.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        return binding.root
    }
}