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

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favAdapter = FavoriteAdapter(TaskRepository.favoriteList) { task ->
            task.isFavorite = false
            TaskRepository.favoriteList.remove(task)
            favAdapter.notifyDataSetChanged()
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favAdapter
        }

        return binding.root
    }
}