package com.example.todolist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.FavoriteAdapter
import com.example.todolist.databinding.FragmentFavoriteBinding
import com.example.todolist.model.Task
import com.example.todolist.ui.DetailTaskActivity

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var favoriteList = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteList = mutableListOf(
            Task("Meeting Client", "23 Nov 2025", "Kerja", true)
        )

        loadList()
        return binding.root
    }

    private fun loadList() {
        favoriteAdapter = FavoriteAdapter(
            favoriteList,
            onClick = {
                val i = Intent(requireContext(), DetailTaskActivity::class.java)
                i.putExtra("DATA", it)
                startActivity(i)
            },
            onUnfavorite = {
                it.isFavorite = false
                favoriteList.remove(it)
                favoriteAdapter.notifyDataSetChanged()
            }
        )

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }
    }
}