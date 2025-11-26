package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.cardNotification.setOnClickListener {
            navigate(NotificationFragment())
        }

        binding.cardPrivacy.setOnClickListener {
            navigate(PrivacyFragment())
        }

        binding.cardTheme.setOnClickListener {
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