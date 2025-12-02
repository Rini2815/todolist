package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.FavoriteAdapter
import com.example.todolist.databinding.FragmentFavoriteBinding
import com.example.todolist.model.TaskRepository

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // Ambil list favorit awal
        favAdapter = FavoriteAdapter(TaskRepository.getFavorites()) { task ->
            // Saat tombol favorit di-klik → matikan favorit
            task.isFavorite = false
            TaskRepository.setFavorite(task.id, false)

            // Refresh list setelah perubahan
            val newList = TaskRepository.getFavorites()
            favAdapter.updateData(newList)
        }

        // Setup RecyclerView
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favAdapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
