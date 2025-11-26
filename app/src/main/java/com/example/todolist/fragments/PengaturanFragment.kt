package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.FragmentPengaturanBinding

class PengaturanFragment : Fragment() {

    private lateinit var binding: FragmentPengaturanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPengaturanBinding.inflate(inflater, container, false)

        binding.btnNotification.setOnClickListener {
            navigate(NotificationFragment())
        }

        binding.btnPrivacy.setOnClickListener {
            navigate(PrivacyFragment())
        }

        binding.btnTheme.setOnClickListener {
            navigate(ThemeFragment())
        }

        return binding.root
    }

    private fun navigate(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(com.example.todoapp.R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}