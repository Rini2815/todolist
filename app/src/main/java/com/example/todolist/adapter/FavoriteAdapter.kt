package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskFavoriteBinding
import com.example.todolist.model.Task

class FavoriteAdapter(
    private var list: MutableList<Task>,
    private val onClick: (Task) -> Unit,
    private val onUnfavorite: (Task) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    inner class FavViewHolder(val binding: ItemTaskFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemTaskFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val item = list[position]

        holder.binding.txtTitle.text = item.title
        holder.binding.txtDate.text = item.date

        // masuk detail tugas
        holder.itemView.setOnClickListener {
            onClick(item)
        }

        // hapus dari favorit
        holder.binding.btnUnfav.setOnClickListener {
            item.isFavorite = false
            notifyItemChanged(position)
            onUnfavorite(item)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: MutableList<Task>) {
        list = newList
        notifyDataSetChanged()
    }
}