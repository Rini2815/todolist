package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemFavoriteBinding
import com.example.todolist.model.Task

class FavoriteAdapter(
    private val favoriteList: MutableList<Task>,
    private val onUnfavoriteClicked: (Task) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    inner class FavViewHolder(val binding: ItemFavoriteBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val task = favoriteList[position]
        holder.binding.apply {
            tvTaskTitle.text = task.title
            tvTaskTime.text = task.time

            btnUnfavorite.setOnClickListener {
                onUnfavoriteClicked(task)
            }
        }
    }

    override fun getItemCount(): Int = favoriteList.size

    fun updateData(newList: MutableList<Task>) {
        favoriteList.clear()
        favoriteList.addAll(newList)
        notifyDataSetChanged()
    }
}
