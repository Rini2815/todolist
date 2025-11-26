package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.FragmentProfileBinding
import com.example.todolist.ui.LoginActivity
import com.example.todolist.ui.EditProfileActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.btnSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(com.example.todoapp.R.id.nav_host_fragment, PengaturanFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnAbout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(com.example.todoapp.R.id.nav_host_fragment, AboutFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnLogout.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return binding.root
    }
}