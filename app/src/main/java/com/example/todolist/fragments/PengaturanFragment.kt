package com.example.todolist.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.activities.LoginActivity
import com.example.todolist.databinding.FragmentPengaturanBinding
import com.example.todolist.utils.MyPreferenceManager

class PengaturanFragment : Fragment() {

    private lateinit var binding: FragmentPengaturanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPengaturanBinding.inflate(inflater, container, false)

        binding.menuLogout.setOnClickListener {
            showLogoutDialog()
        }

        return binding.root
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda yakin ingin keluar?")
            .setPositiveButton("Yes") { _, _ ->
                MyPreferenceManager(requireContext()).clear()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("No", null)
            .show()
    }
}
